/**
 * Dynamic datasource routing based on thread-local context.
 */
package com.example.api_first.config.datasource.routing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Custom routing datasource that extends Spring's AbstractRoutingDataSource.
 * Determines the target datasource (READ or WRITE) based on the current
 * thread's DataSourceContextHolder value.
 */
public class RoutingDataSource extends AbstractRoutingDataSource {

    /**
     * Logger for debugging datasource routing decisions.
     */
    private static final Logger log = LoggerFactory.getLogger(RoutingDataSource.class);

    /**
     * Determines the lookup key for the current datasource.
     * Returns the datasource type from context, defaulting to WRITE if not set.
     *
     * @return the DataSourceType to use for the current operation.
     */
    @Override
    protected Object determineCurrentLookupKey() {
        DataSourceType type = DataSourceContextHolder.getDataSourceType();
        if (type == null) {
            type = DataSourceType.WRITE;
        }
        log.debug("Routing to datasource: {}", type);
        return type;
    }
}
