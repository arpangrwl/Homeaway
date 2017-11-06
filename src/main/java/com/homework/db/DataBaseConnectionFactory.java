package com.homework.db;

import org.apache.log4j.Logger;

/**
 * Created by Arpan on 11/5/17.
 *
 * This class provides the type of database connection based on provided Database name
 *
 */


public class DataBaseConnectionFactory {
    private static final Logger logger = Logger.getLogger(DataBaseConnectionFactory.class);

    public static DatabaseConnection getDatabase(String databaseName) {

        if ("mysql".equalsIgnoreCase(databaseName)) {
            logger.info("Selected Database is MYSQL");
            return new MySqlDatabase();
        } else if ("oracle".equalsIgnoreCase(databaseName)) {
            logger.info("Selected Database is oracle");
            return new OracleDatabase();
        } else if ("postgresql".equalsIgnoreCase(databaseName)) {
            logger.info("Selected Database is postgresql");
            return new PostgreSqlDatabase();
        }

        return null;
    }
}
