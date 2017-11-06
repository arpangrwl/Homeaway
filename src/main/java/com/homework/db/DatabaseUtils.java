package com.homework.db;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Arpan on 11/5/17.
 *
 * This class contains some database basic utility functions
 *
 */

public class DatabaseUtils {
    private static final Logger logger = Logger.getLogger(DatabaseUtils.class);

    public static Properties prop = null;

    /**
     *  This method reads database.properties required for DB connections
     *
     */
    public static void setDatabaseProperties() {

        prop = new Properties();
        InputStream input = null;

        try {
            input = DatabaseUtils.class.getClassLoader().getResourceAsStream("database.properties");
            if (input == null) {
                logger.error("Unable to find database.properties file");
                return;
            }

            //load a properties file from class path, inside static method
            prop.load(input);

        } catch (IOException ex) {
            logger.error("Unable to conect to Database, network issue", ex);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    logger.error("Unable to to close the connection", e);
                }
            }
        }
    }

//    public static void main(String[] args) {
//        DatabaseUtils.setDatabaseProperties();
//    }

}
