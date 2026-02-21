/**
 * Enumeration of available datasource types for read/write separation.
 */
package com.example.api_first.config.datasource.routing;

/**
 * Enum representing the type of datasource to use.
 * Used as lookup keys in RoutingDataSource to route to the appropriate database.
 */
public enum DataSourceType {

    /**
     * Read-only datasource for SELECT queries.
     * Typically points to a read replica for load distribution.
     */
    READ,

    /**
     * Write datasource for INSERT, UPDATE, DELETE queries.
     * Points to the primary database.
     */
    WRITE
}
