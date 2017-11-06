package com.homework.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Arpan on 11/5/17.
 */
public class DatabaseUtils {
    public static Properties prop = null;

    public static void setDatabaseProperties() {

       prop = new Properties();
        InputStream input = null;

        try {
            input = DatabaseUtils.class.getClassLoader().getResourceAsStream("database.properties");
            if (input == null) {
                System.out.println("Sorry, unable to find database.properties file" );
                return;
            }

            //load a properties file from class path, inside static method
            prop.load(input);

//            get the property value and print it out
//            System.out.println(prop.getProperty("mysql.Driver"));
//            System.out.println(prop.getProperty("mysql.UserName"));
//            System.out.println(prop.getProperty("mysql.Password"));

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    public static void main(String[] args) {
//        DatabaseUtils.setDatabaseProperties();
//    }

}
