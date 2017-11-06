package com.homework.db;

/**
 * Created by Arpan on 11/5/17.
 */
public class DataBaseConnectionFactory {

    public static DatabaseConnection getDatabase(String databaseName) {

        if ("mysql".equalsIgnoreCase(databaseName))
            return new MySqlDatabase();
        else if ("oracle".equalsIgnoreCase(databaseName))
            return new OracleDatabase();
        else if ("postgresql".equalsIgnoreCase(databaseName))
            return new PostgreSqlDatabase();

        return null;
    }
}
