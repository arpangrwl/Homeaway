package com.homework.rest;


import com.homework.db.DataBaseConnectionFactory;
import com.homework.db.DatabaseConnection;
import org.apache.log4j.Logger;
import java.util.ArrayList;

/**
 * Created by Arpan on 11/5/17.
 */
public class RestAPIs {
    private static final Logger logger = Logger.getLogger(RestAPIs.class);

    public static void restoreGitHubFileOnDataBase(String gitHubURL, String fileExtension, String DatabaseName) {

        DatabaseConnection pc = DataBaseConnectionFactory.getDatabase(DatabaseName);
        for (String repository : RestAPIsUtils.getListOfUserRepositories(gitHubURL)) {
            logger.debug("Working repository name " + repository);
            pc.saveRecordsUsersGitRepoDetails(RestAPIsUtils.getUserName(gitHubURL), repository, fileExtension, RestAPIsUtils.getListedFilesOfFileExtension
                    (gitHubURL, repository, fileExtension));

        }
    }

    public static ArrayList<String> listOfMatchingDatabaseAndRestAPIRecords(String gitHubURL, String fileExtension, String DatabaseName){
        ArrayList<String> listOfGitRecords = new ArrayList<String>();
        ArrayList<String> listOfDBRecords = new ArrayList<String>();

        //Get the List of records for Github
        for (String repository : RestAPIsUtils.getListOfUserRepositories(gitHubURL))
            listOfGitRecords.addAll(RestAPIsUtils.getListedFilesOfFileExtension(gitHubURL, repository, fileExtension));

        //Get the list of existing records for Database
        DatabaseConnection pc = DataBaseConnectionFactory.getDatabase(DatabaseName);
        for (String repo : RestAPIsUtils.getListOfUserRepositories(gitHubURL))
            listOfDBRecords.addAll(pc.getRequestedUsersRecords(RestAPIsUtils.getUserName(gitHubURL), repo, fileExtension));

        ArrayList<String> matchingRecords = new ArrayList<String>(listOfGitRecords);
        matchingRecords.retainAll(listOfDBRecords);

        return matchingRecords;
    }

}
