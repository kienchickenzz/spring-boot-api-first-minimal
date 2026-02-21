/**
 * Thread-local holder for current datasource type context.
 */
package com.example.api_first.config.datasource.routing;

/**
 * Utility class that holds the current datasource type in a ThreadLocal variable.
 * Used by RoutingDataSource to determine which datasource to use for the current thread.
 * This class is thread-safe and ensures each request gets its own datasource context.
 */
public final class DataSourceContextHolder {

    /**
     * ThreadLocal storage for the current datasource type.
     */
    private static final ThreadLocal<DataSourceType> CONTEXT = new ThreadLocal<>();

    /**
     * Private constructor to prevent instantiation.
     */
    private DataSourceContextHolder() {
    }

    /**
     * Sets the datasource type for the current thread.
     *
     * @param dataSourceType the datasource type to set (READ or WRITE).
     */
    public static void setDataSourceType(DataSourceType dataSourceType) {
        CONTEXT.set(dataSourceType);
    }

    /**
     * Gets the datasource type for the current thread.
     *
     * @return the current datasource type, or null if not set.
     */
    public static DataSourceType getDataSourceType() {
        return CONTEXT.get();
    }

    /**
     * Clears the datasource type for the current thread.
     * Should be called after each request to prevent memory leaks.
     */
    public static void clear() {
        CONTEXT.remove();
    }
}
