Problem statement :- 

Create a Java-based REST API endpoint that takes in the following inputs
1. A set of Github orgs (e.g. https://github.com/seiyria )
2. A set of file extensions (e.g. java, css, js)
3. Name of a database (for Mongo/MySQL/MSSQL etc) or keyspace (for C*/DynamoDB etc) on
candidate’s database platform

This application should use Github’s REST APIs directly (and not​ an SDK) to find out all files in the list of
Github Orgs (you need to read all available repos and branches in the orgs) that have file extensions that
match the set of file extensions provided as input. It should then store references to these files in the
database provided as input.
The application’s requirements are kept intentionally broad, to give the candidate flexibility in designing
this app. However, the candidate might be asked to explain why​ his/her design does what it does.

Create another Java-based REST API endpoint that takes in the following inputs
1. Optional input: Github org
2. Name of a database (for Mongo/MySQL/MSSQL etc) or keyspace (for C*/DynamoDB etc) on
candidate’s database platform
3. A set of file extensions (e.g. java, css, js)
This endpoint returns the list of files from the database, that match the inputs.



Projects Limitations :- 
Below are the limitation of the project.  Github API only allow 60 Rest API request to be fired in an hour. (https://developer.github.com/v3/#rate-limiting)
And to get all the file contents for a Git repository one has to recursively fire git API request to retrieve file name.
So it is very much possible that these 60 GIT API request will get consumed while working on a single Users Repository.


Solutions:-
Below API will store all the files related to gitHub URL of fileExtension to a give database name 
RestAPIs.restoreGitHubFileOnDataBase(gitHubURL, fileExtension, DatabaseName);

Below API returns the list of files from the database, that match the inputs.
RestAPIs.restoreGitHubFileOnDataBase(gitHubURL, fileExtension, DatabaseName);
