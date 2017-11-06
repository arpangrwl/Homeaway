package com.homework.db;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arpan on 11/5/17.
 *
 * This class contain function for maintain DB connection, Creating DB table , inserting values in DB,
 * and firing select statement on database.
 *
 */
public class MySqlDatabase implements DatabaseConnection {
    private static final Logger logger = Logger.getLogger(MySqlDatabase.class);

    private String driver;
    private String connectionURL;
    private String userName;
    private String password;

    /**
     * This default constructor is used in getting DB properties from database.properties files
     * and setting method variables.
     *
     */
    MySqlDatabase() {
        DatabaseUtils.setDatabaseProperties();
        driver = DatabaseUtils.prop.getProperty("mysql.Driver");
        connectionURL = DatabaseUtils.prop.getProperty("mysql.ConnectionURL");
        userName = DatabaseUtils.prop.getProperty("mysql.UserName");
        password = DatabaseUtils.prop.getProperty("mysql.Password");
    }

    /**
     * This method is for establishing DB connection
     * @return Connection of DB
     */
    @Override
    public Connection establishDBConnection() {
        Connection connection = null;

        try {
            // create our mysql database connection
            Class.forName(getDriver());
            connection = DriverManager.getConnection(getConnectionURL(), getUserName(), getPassword());
        } catch (Exception e) {
            logger.error("Unable to connect to DB",e);
        }
        return connection;
    }

    /**
     *  Creating table UsersGitRepoDetails
     *  DB Column names :- userName, repository, fileExtension, fileName
     *
     */
    public void createBaseTableIfNotExist() {
        MySqlDatabase mySqlDatabase = new MySqlDatabase();
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        DatabaseMetaData md = null;
        ResultSet rs = null;

        String createTableSQL = "CREATE TABLE UsersGitRepoDetails("
                + "userName VARCHAR(20) NOT NULL, "
                + "repository VARCHAR(20) NOT NULL, "
                + "fileExtension VARCHAR(20) NOT NULL, "
                + "fileName VARCHAR(200) NOT NULL, " + "PRIMARY KEY (fileName) "
                + ")";

        try {
            dbConnection = mySqlDatabase.establishDBConnection();
            md = dbConnection.getMetaData();
            rs = md.getTables(null, null, "%", null);

            //If table exist than dont create the table.
            while (rs.next()) {
                if (rs.getString(3).equals("UsersGitRepoDetails")) {
                    return;
                }
            }

            preparedStatement = dbConnection.prepareStatement(createTableSQL);

            // execute the SQL stetement
            preparedStatement.executeUpdate();
            logger.info("Table \"UsersGitRepoDetails\" is created!");

        } catch (SQLException e) {
            logger.error("Unable to create the SQL table :- ", e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (dbConnection != null) {
                    dbConnection.close();
                }
            } catch (SQLException e) {
                logger.error("Unable to close the DB clonection :- ", e);
            }
        }
    }

    /**
     * This is for saving records at UsersGitRepoDetails
     * @param userName  name of user
     * @param repository  Name of repository
     * @param fileExtension File Extension
     * @param listOfFileName  HTML link of file name
     *
     */
    public void saveRecordsUsersGitRepoDetails(String userName, String repository, String fileExtension, List<String> listOfFileName) {
        MySqlDatabase mySqlDatabase = new MySqlDatabase();
        createBaseTableIfNotExist();
        int counter = 0;
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        String insertTableSQL = "INSERT INTO UsersGitRepoDetails"
                + "(userName, repository, fileExtension, fileName) VALUES"
                + "(?,?,?,?)";

        try {
            dbConnection = mySqlDatabase.establishDBConnection();
            preparedStatement = dbConnection.prepareStatement(insertTableSQL);

            for (String fileName : listOfFileName) {
                preparedStatement.setString(1, userName);
                preparedStatement.setString(2, repository);
                preparedStatement.setString(3, fileExtension);
                preparedStatement.setString(4, fileName);

                try {
                    preparedStatement.executeUpdate();
                }catch(SQLException e){
                    logger.info("Record already present, skipping filename :- "+ fileName);
                }
            }

            logger.info("Records are inserted into UsersGitRepoDetails table!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (dbConnection != null) {
                    dbConnection.close();
                }
            } catch (SQLException e) {
                logger.error("Unable to close the DB clonection :- ", e);
            }
        }
    }

    /**
     * Select statement on UsersGitRepoDetails table to get the list of Filename associated with user's repository for any given extension
     * @param userName  name of user
     * @param repository Repo name
     * @param fileExtension file extension name
     * @return List of File name
     */
    public ArrayList<String> getRequestedUsersRecords(String userName, String repository, String fileExtension) {
        ArrayList<String> listOfRecords = new ArrayList<String>();
        MySqlDatabase mySqlDatabase = new MySqlDatabase();
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        String selectSQL = "SELECT fileName FROM UsersGitRepoDetails WHERE userName = ? and repository = ? and fileExtension = ?";

        try {
            dbConnection = mySqlDatabase.establishDBConnection();
            preparedStatement = dbConnection.prepareStatement(selectSQL);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, repository);
            preparedStatement.setString(3, fileExtension);

            // execute select SQL stetement
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                listOfRecords.add(rs.getString("fileName"));
            }
        } catch (SQLException e) {
            logger.error("Unable to execute the query :- ", e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (dbConnection != null) {
                    dbConnection.close();
                }
            } catch (SQLException e) {
                logger.error("Unable to close the DB clonection :- ", e);
            }
        }
        return listOfRecords;
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

//    public static void main(String[] args) {
//        DatabaseUtils.setDatabaseProperties();
//        System.out.println(DatabaseUtils.prop.getProperty("mysql.Driver"));
//
//        MySqlDatabase mySqlDatabase = new MySqlDatabase();
//        Connection connection = mySqlDatabase.establishDBConnection();
//    }
}

