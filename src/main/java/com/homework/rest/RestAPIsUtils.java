package com.homework.rest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Arpan on 11/5/17.
 */
public class RestAPIsUtils {

    public static String getRestAPIRequest(String URLString) {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try {
            URL url = new URL(URLString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;

            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /*
    input gitHubURL : - "https://github.com/username"
     */
    public static String getRepositoryURLString(String gitHubURL) {
        String userName = gitHubURL.replace(RestAPIConstants.gitHubBaseLink, "");
        return RestAPIConstants.usersRepositoriesURL.replace("USERNAME", userName);
    }

    public static ArrayList<String> getListOfUserRepositories(String gitHubURL) {

        String repositoryURLResults = RestAPIsUtils.getRestAPIRequest(RestAPIsUtils.getRepositoryURLString(gitHubURL));
        ArrayList<String> listOfRepositories = new ArrayList<String>();

        JSONArray jsonarray = new JSONArray(repositoryURLResults);
        JSONObject jsonobject;

        for (int i = 0; i < jsonarray.length(); i++) {
            jsonobject = jsonarray.getJSONObject(i);
            listOfRepositories.add(jsonobject.getString("name"));
        }
        return listOfRepositories;
    }

    public static ArrayList<String> getListedFilesOfFileExtension(String gitHubURL, String repository, String fileExtension) {

        ArrayList<String> listOfFileAndDirectories = new ArrayList<String>();
        String userName = gitHubURL.replace(RestAPIConstants.gitHubBaseLink, "");
        String fileOrDirName = "";
        String gitRepositoryPath = "";
        String fileType = "";

        String baseRepositoryURL = RestAPIConstants.repositoryContentURL.replace("REPOSITORY", repository).replace("USERNAME", userName);
        String repositoryURLResults = RestAPIsUtils.getRestAPIRequest(baseRepositoryURL);

        JSONArray jsonarray = new JSONArray(repositoryURLResults);
        JSONObject jsonobject;

        for (int i = 0; i < jsonarray.length(); i++) {
            jsonobject = jsonarray.getJSONObject(i);
            fileOrDirName = jsonobject.getString("name");
            gitRepositoryPath = jsonobject.getString("path");
            fileType = jsonobject.getString("type");

            if (fileType.equals("file")) {

                if (checkFileExtension(fileOrDirName, fileExtension))
                    listOfFileAndDirectories.add(jsonobject.getString("html_url"));

            } else if (fileType.equals("dir")) {
                listOfFileAndDirectories.addAll(getListOfFileFromDirectory(baseRepositoryURL,gitRepositoryPath, fileExtension));
            }

        }

        return listOfFileAndDirectories;
    }

    private static ArrayList<String> getListOfFileFromDirectory(String baseRepositoryURL, String gitRepositoryPath, String fileExtension) {
        ArrayList<String> listOfFiles = new ArrayList<String>();
        String fileOrDirName = "";
        String fileType = "";

        String baseRepositoryURLWithPath = baseRepositoryURL + "/" + gitRepositoryPath ;
        String repositoryURLResults = RestAPIsUtils.getRestAPIRequest(baseRepositoryURLWithPath);

        JSONArray jsonarray = new JSONArray(repositoryURLResults);
        JSONObject jsonobject;

        for (int i = 0; i < jsonarray.length(); i++) {
            jsonobject = jsonarray.getJSONObject(i);
            fileOrDirName = jsonobject.getString("name");
            gitRepositoryPath = jsonobject.getString("path");
            fileType = jsonobject.getString("type");

            if (fileType.equals("file")) {
                if (checkFileExtension(fileOrDirName, fileExtension))
                    listOfFiles.add(jsonobject.getString("html_url"));
            } else if (fileType.equals("dir")) {
                listOfFiles.addAll(getListOfFileFromDirectory(baseRepositoryURL,gitRepositoryPath, fileExtension));
            }

        }

        return listOfFiles;
    }

    private static boolean checkFileExtension(String fileOrDirName, String fileExtension) {

        String fileType = fileOrDirName.substring(fileOrDirName.lastIndexOf(".") + 1);

        if (fileType.equals(fileExtension))
            return true;
        else
            return false;
    }

    public static String getUserName(String gitHubURL) {
        return gitHubURL.replace(RestAPIConstants.gitHubBaseLink, "");
    }

//    public static void main(String[] args) {
//        ArrayList<String> listOfFiles = getListOfFileFromDirectory("https://api.github.com/repos/arpangrwl/RoughProj/contents", "src/main/java", "java") ;
//
//        for(String s : listOfFiles){
//            System.out.println(s);
//        }
//    }

}
