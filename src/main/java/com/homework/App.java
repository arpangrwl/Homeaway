package com.homework;

import com.homework.rest.RestAPIs;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Arpan on 11/5/17.
 */

public class App {
    private static final Logger logger = Logger.getLogger(App.class);

    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);
        System.out.println("Please enter the Github repository example:- \"https://github.com/arpangrwl\" ");
        String gitHubURL = reader.nextLine();

        reader = new Scanner(System.in);
        System.out.println("Please enter the file extension example:- \"java\"  or  \"txt\" ");
        String fileExtension = reader.nextLine();

        reader = new Scanner(System.in);
        System.out.println("Please enter the name of database you want to insert data in example:- \"mysql\" ");
        String DatabaseName = reader.nextLine();

        logger.debug("Input Parameters Passed Github repository:- " + gitHubURL + "\n file extension:- " + fileExtension
        + "\n Database Name:- " + DatabaseName );
 /*
Create a Java-based REST API endpoint that takes in the following inputs
1. A set of Github orgs (e.g. https://github.com/seiyria )
2. A set of file extensions (e.g. java, css, js)
3. Name of a database (for Mongo/MySQL/MSSQL etc) or keyspace (for C* /DynamoDB etc) on candidate’s database platform
*/
        RestAPIs.restoreGitHubFileOnDataBase(gitHubURL, fileExtension, DatabaseName);


/*
Create another Java-based REST API endpoint that takes in the following inputs
1. Optional input: Github org
2. Name of a database (for Mongo/MySQL/MSSQL etc) or keyspace (for C* /DynamoDB etc) on candidate’s database platform
3. A set of file extensions (e.g. java, css, js)
This endpoint returns the list of files from the database, that match the inputs.
*/
        ArrayList<String> matchingFiles = RestAPIs.listOfMatchingDatabaseAndRestAPIRecords(gitHubURL, fileExtension, DatabaseName);
        matchingFiles.stream().forEach(System.out::print);
    }

}