package com.homework.db;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arpan on 11/5/17.
 */
public interface DatabaseConnection {

    public Connection establishDBConnection();
    public ArrayList<String> getRequestedUsersRecords(String userName, String repository, String fileExtension) ;
    public void saveRecordsUsersGitRepoDetails(String userName, String repository, String fileExtension, List<String> listOfFileName) ;
    public void createBaseTableIfNotExist() ;

    }
