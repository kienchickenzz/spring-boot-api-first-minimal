/**
 * Configuration for multiple datasources with read/write separation.
 */
package com.example.api_first.config.datasource;

import com.example.api_first.config.datasource.routing.DataSourceType;
import com.example.api_first.config.datasource.routing.RoutingDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Spring configuration class for datasource beans.
 * Creates separate read and write datasources with HikariCP connection pooling,
 * and a routing datasource that delegates to the appropriate target based on context.
 */
@Configuration
public class DataSourceConfig {

    /**
     * Creates the write datasource for database write operations.
     *
     * @param url the JDBC URL for the write database.
     * @param username the database username.
     * @param password the database password.
     * @param driverClassName the JDBC driver class name.
     * @param poolName the name for the HikariCP connection pool.
     * @param maxPoolSize the maximum number of connections in the pool.
     * @param minIdle the minimum number of idle connections.
     * @param idleTimeout the maximum time a connection can sit idle in the pool.
     * @param maxLifetime the maximum lifetime of a connection in the pool.
     * @param keepaliveTime the interval for keepalive checks.
     * @param validationTimeout the timeout for connection validation.
     * @return the configured HikariDataSource for write operations.
     */
    @Bean
    public HikariDataSource writeDataSource(
            @Value("${spring.datasource.write.url}") String url,
            @Value("${spring.datasource.write.username}") String username,
            @Value("${spring.datasource.write.password}") String password,
            @Value("${spring.datasource.write.driver-class-name}") String driverClassName,
            @Value("${spring.datasource.write.hikari.pool-name}") String poolName,
            @Value("${spring.datasource.write.hikari.maximum-pool-size}") int maxPoolSize,
            @Value("${spring.datasource.write.hikari.minimum-idle}") int minIdle,
            @Value("${spring.datasource.write.hikari.idle-timeout}") long idleTimeout,
            @Value("${spring.datasource.write.hikari.max-lifetime}") long maxLifetime,
            @Value("${spring.datasource.write.hikari.keepalive-time}") long keepaliveTime,
            @Value("${spring.datasource.write.hikari.validation-timeout}") long validationTimeout) {

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driverClassName);
        config.setPoolName(poolName);
        config.setMaximumPoolSize(maxPoolSize);
        config.setMinimumIdle(minIdle);
        config.setIdleTimeout(idleTimeout);
        config.setMaxLifetime(maxLifetime);
        config.setKeepaliveTime(keepaliveTime);
        config.setValidationTimeout(validationTimeout);
        config.setReadOnly(false);

        return new HikariDataSource(config);
    }

    /**
     * Creates the read datasource for database read operations.
     *
     * @param url the JDBC URL for the read database.
     * @param username the database username.
     * @param password the database password.
     * @param driverClassName the JDBC driver class name.
     * @param poolName the name for the HikariCP connection pool.
     * @param maxPoolSize the maximum number of connections in the pool.
     * @param minIdle the minimum number of idle connections.
     * @param idleTimeout the maximum time a connection can sit idle in the pool.
     * @param maxLifetime the maximum lifetime of a connection in the pool.
     * @param keepaliveTime the interval for keepalive checks.
     * @param validationTimeout the timeout for connection validation.
     * @return the configured HikariDataSource for read operations.
     */
    @Bean
    public HikariDataSource readDataSource(
            @Value("${spring.datasource.read.url}") String url,
            @Value("${spring.datasource.read.username}") String username,
            @Value("${spring.datasource.read.password}") String password,
            @Value("${spring.datasource.read.driver-class-name}") String driverClassName,
            @Value("${spring.datasource.read.hikari.pool-name}") String poolName,
            @Value("${spring.datasource.read.hikari.maximum-pool-size}") int maxPoolSize,
            @Value("${spring.datasource.read.hikari.minimum-idle}") int minIdle,
            @Value("${spring.datasource.read.hikari.idle-timeout}") long idleTimeout,
            @Value("${spring.datasource.read.hikari.max-lifetime}") long maxLifetime,
            @Value("${spring.datasource.read.hikari.keepalive-time}") long keepaliveTime,
            @Value("${spring.datasource.read.hikari.validation-timeout}") long validationTimeout) {

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driverClassName);
        config.setPoolName(poolName);
        config.setMaximumPoolSize(maxPoolSize);
        config.setMinimumIdle(minIdle);
        config.setIdleTimeout(idleTimeout);
        config.setMaxLifetime(maxLifetime);
        config.setKeepaliveTime(keepaliveTime);
        config.setValidationTimeout(validationTimeout);
        config.setReadOnly(true);

        return new HikariDataSource(config);
    }

    /**
     * Creates the primary routing datasource that delegates to read or write datasource.
     * Uses DataSourceContextHolder to determine which target datasource to use.
     * Defaults to write datasource when no context is set.
     *
     * @param writeDataSource the datasource for write operations.
     * @param readDataSource the datasource for read operations.
     * @return the configured RoutingDataSource as the primary datasource.
     */
    @Bean
    @Primary
    public DataSource dataSource(
            @Qualifier("writeDataSource") DataSource writeDataSource,
            @Qualifier("readDataSource") DataSource readDataSource) {

        RoutingDataSource routingDataSource = new RoutingDataSource();

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceType.WRITE, writeDataSource);
        targetDataSources.put(DataSourceType.READ, readDataSource);

        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(writeDataSource);
        routingDataSource.afterPropertiesSet();

        return routingDataSource;
    }
}
