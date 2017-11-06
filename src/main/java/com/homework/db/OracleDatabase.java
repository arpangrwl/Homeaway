package com.homework.db;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arpan on 11/5/17.
 */

//Dummy Class for future need.
public class OracleDatabase implements DatabaseConnection {
    private String driver;
    private String connectionURL;
    private String userName;
    private String password;

    OracleDatabase() {
        DatabaseUtils.setDatabaseProperties();
        driver = DatabaseUtils.prop.getProperty("oracle.Driver");
        connectionURL = DatabaseUtils.prop.getProperty("oracle.ConnectionURL");
        userName = DatabaseUtils.prop.getProperty("oracle.UserName");
        password = DatabaseUtils.prop.getProperty("oracle.Password");
    }

    @Override
    public Connection establishDBConnection() {
        return null;
    }

    @Override
    public ArrayList<String> getRequestedUsersRecords(String userName, String repository, String fileExtension) {
        return null;
    }

    @Override
    public void saveRecordsUsersGitRepoDetails(String userName, String repository, String fileExtension, List<String> listOfFileName) {

    }

    @Override
    public void createBaseTableIfNotExist() {

    }

    private String getDriver() {
        return driver;
    }

    private String getConnectionURL() {
        return connectionURL;
    }

    private String getUserName() {
        return userName;
    }

    private String getPassword() {
        return password;
    }
}
