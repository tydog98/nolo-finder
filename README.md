This program is created to determine which courses from NCC are no/low cost courses

Requires JavaFX, OpenCSV, and Apache Commons Lang to build (should all be handled by maven).

Also supports commandline functionality

Parameters:

noloThreshhold bookstoreFileLocation [roomlistFileLocation] outputFile

How to build:
1. Install Maven onto your machine (https://maven.apache.org/download.cgi) or open the project with a Maven supported IDE
2. From the commandline run "mvn clean build" in the root directory of the project. If you are using an IDE use the built in commandline there.
3. The executable JAR file will be found at projectname/target/nolofinder-1.0-SNAPSHOT (or however you have it set to be named in the pom.xml)
