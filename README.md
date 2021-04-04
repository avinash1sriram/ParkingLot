ParkingLot Project


======================================================================================
Java version : 1.8

Maven : 3

Junit : 4.11

==================================================================================

-- Installing instructions of above softwares on Mac

brew install --cask adoptopenjdk8

If homebrew is not present use this command : /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

By default Mac comes with Maven. If not present execute this "brew install maven"


=====================================================================================

-- Installing instructions of above softwares on Ubuntu

sudo apt-get install openjdk-8*

sudo apt install maven

Use mvn -version to verify the installation of maven

Ref : https://linuxize.com/post/how-to-install-apache-maven-on-ubuntu-18-04/

Include JUnit in the pom.xml as dependency

=================================================================================================

Running the project:

-- After cloning the project

-- Do open project directory in the terminal

-- Do mvn clean install

-- then run the following command -  java -jar target/ParkingLot-1.0-SNAPSHOT.jar [Absolute path of input file]


