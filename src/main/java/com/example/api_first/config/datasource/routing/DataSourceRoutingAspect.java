/**
 * AOP aspect for automatic datasource routing based on @Transactional annotation.
 */
package com.example.api_first.config.datasource.routing;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Aspect that intercepts methods annotated with @Transactional and sets
 * the appropriate datasource context before execution.
 * Routes to READ datasource for readOnly transactions, WRITE otherwise.
 * Implements Ordered to ensure it runs before the transaction aspect.
 */
@Aspect
@Component
public class DataSourceRoutingAspect implements Ordered {

    /**
     * Logger for debugging routing decisions.
     */
    private static final Logger log = LoggerFactory.getLogger(DataSourceRoutingAspect.class);

    /**
     * Returns the order of this aspect.
     * Uses -100 to ensure this aspect runs before Spring's transaction aspect,
     * so the datasource context is set before the transaction begins.
     *
     * @return the order value (-100).
     */
    @Override
    public int getOrder() {
        return -100;
    }

    /**
     * Around advice that intercepts @Transactional annotated methods.
     * Sets the datasource context based on the readOnly attribute of the annotation.
     *
     * @param joinPoint the join point representing the intercepted method.
     * @return the result of the method execution.
     * @throws Throwable if the method execution throws an exception.
     */
    @Around("@annotation(org.springframework.transaction.annotation.Transactional)")
    public Object routeDataSource(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Transactional transactional = signature.getMethod().getAnnotation(Transactional.class);

        if (transactional == null) {
            transactional = joinPoint.getTarget().getClass().getAnnotation(Transactional.class);
        }

        DataSourceType dataSourceType = (transactional != null && transactional.readOnly())
                ? DataSourceType.READ
                : DataSourceType.WRITE; // Default to WRITE if no @Transactional is present

        log.debug("Setting datasource context to: {} for method: {}",
                dataSourceType, signature.getMethod().getName());

        DataSourceContextHolder.setDataSourceType(dataSourceType);

        try {
            return joinPoint.proceed();
        } finally {
            DataSourceContextHolder.clear();
        }
    }
}
