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



Log :-
Connected to the target VM, address: '127.0.0.1:64789', transport: 'socket'
Please enter the Github repository example:- "https://github.com/arpangrwl"

https://github.com/nipunalways

Please enter the file extension example:- "java"  or  "txt"

java

Please enter the name of database you want to insert data in example:- "mysql"

mysql

2017-11-06 00:14:22 INFO  DataBaseConnectionFactory:14 - Selected Database is MYSQL
2017-11-06 00:18:21 INFO  MySqlDatabase:74 - Table "UsersGitRepoDetails" is created!
2017-11-06 00:18:21 INFO  MySqlDatabase:120 - Records are inserted into UsersGitRepoDetails table!
2017-11-06 00:18:42 INFO  MySqlDatabase:120 - Records are inserted into UsersGitRepoDetails table!
2017-11-06 00:18:46 INFO  MySqlDatabase:120 - Records are inserted into UsersGitRepoDetails table!
2017-11-06 00:18:46 INFO  MySqlDatabase:120 - Records are inserted into UsersGitRepoDetails table!
Disconnected from the target VM, address: '127.0.0.1:64789', transport: 'socket'

Process finished with exit code 0


Inserted Values in DB

mysql> select * from UsersGitRepoDetails;
+-------------+------------+---------------+----------------------------------------------------------------------------------
| userName    | repository | fileExtension | fileName                                                                          |
+-------------+------------+---------------+----------------------------------------------------------------------------------
| nipunalways | MyCode     | java          | https://github.com/nipunalways/MyCode/blob/master/src/List/ComparatorExample.java |
| nipunalways | MyCode     | java          | https://github.com/nipunalways/MyCode/blob/master/src/List/Cursors.java           |
| nipunalways | MyCode     | java          | https://github.com/nipunalways/MyCode/blob/master/src/List/ListExamples.java      |
| nipunalways | MyCode     | java          | https://github.com/nipunalways/MyCode/blob/master/src/List/Test.java              |
+-------------+------------+---------------+-----------------------------------------------------------------------------------+
4 rows in set (0.00 sec)
