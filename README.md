# MK Comer - Microsoft Take Home Engineering Challenge


The Taxi API allows us to filter cab data on a local postgres database containing some populated cab data. The taxi-api and postgres database can be built and run locally using maven and docker. Additionally, to test the API, there is a provided python script to populate the local postgres database and a test script which tests the taxi-api based on various query parameters. Please follow instructions below to build, run and test this code.

Tools & Frameworks: Maven, Docker, Tomcat, Java, Jersey, Hibernate, Postgres, Python (pandas, psycopg2, sqlalchemy, requests)

Services: Taxi API  & Postgres Database

#Building the Taxi API: 
Tools needed: Docker & Maven 

1- First install maven and docker if you do not have these packages locally:
* brew cask install docker
* brew install maven

2- Clone the repository. Please stay in working directory = microsoft-challenge for the remainder of the build and run code instructions.
* git clone 

3-Next build the Taxi API docker image via the below command:
* mvn clean install -P build-docker -pl taxi-api

4-Check that the taxi image was created with the below command (You should see the taxi-api:latest image listed in your local docker images) 
* docker images


#Run Code:
1-Spin up the local postgres database and the taxi api using docker compose by running on your command line:
 * docker-compose up 
 
    (Leave this terminal window running - or else the services will be killed)
 
2-Check that the Taxi-API is running via your web browser - it should return "Taxi API Service Active":
* In your browser, go to -  http://localhost:8080/taxi-api/_healthcheck


#Test Taxi API
Tools/packages needed: python, pandas, psycopg2, sqlalchemy, requests

1 - Open up a new terminal (not the terminal you ran docker-compose up). 

2- Install packages needed to test: 
* brew install python
* pip install pandas
* pip install psycopg2
* pip install sqlalchemy
* pip install requests

2- Change directory to microsoft-challenge/testdata via command:
* cd testdata

3 - Populate the local postgres database by running the below script. (For the purposes of time/quickly testing, we will be populating the DB with a subset of the January Cab Data - 100 data points/cab type).
* python populateDB.py

3- Run Script to test API endpoints
* python testAPI.py


#Extra Notes: API Query Parameters 

Borough API: Returns Location ID by Borough, and Zone if specified
* Path: /taxi-api/taxi/locationId/
* ?borough= 
* &zone=

Taxi API: Returns taxi history by type, start and end location and start and end time if specified
* Path: /taxi-api/taxi/history/
* ?taxiType= 
* &startLocation=
* &endLocation=
* &startTime=
* &endTime=

# Technology Choices - Trade-offs/notes
Hibernate: great tool for ORM & RDS DB; however, it is not great with batch operations. This is why I decided to populate the DB using python (specifically sqlalchemy) and tested the code via a python script. Additionally, Hibernate supports schema validation between DAOs and Postgres DB (great for this use case - where our DB was created via python and our DAO methods utilize hibernate to make queries).

Postgres: Widely used (widely used in production as well) and a RDS database I am familiar with.

Docker: Great for entire development ecosystem in a singular application. Docker also supports the ability to integrate code, runtime, system tools, and libraries for our taxi api.

Docker-Compose: The Docker-Compose file simplifies deployment and ability to deploy locally. It is great use for a single machine (in this use case) where we don't need to distribute containers across many different machines; however, it is not great for production (I would likely use a container management/orchestration platform to support load-balancing, availability, scaling, db, etc if put into production). We can still use the taxi api docker image regardless, if we were to put this into production or deploy it to a container management platform. 

Jersey: Makes the development of RESTFul services easier and a framework I am familiar with for building out APIs.


# TODO 
If I had more time: 
* Develop Full CRUD operations surrounding taxi data and develop full Mockito unit tests
* Develop a better domain model and table structure for the system - one that supports a system of scale
* Cleanse the data more thoroughly before inserting to db (issues with whitespaces in borough data)
* Better validation/error handling around query parameters and data structure for input parameters (return specific 400 response)
* Potentially create joins/relationship between Borough data and Taxi Data & more complex query
* Spin up Swagger UI Documentation - to help abstract out code for users and better document the API 
* If creating more dependent modules in this system, I would create an interface for this API to enable dependency injection
* Spin up entire an entire database with many years of taxi data to provide historic trip data (not just a small subset) and see how the queries perform (any indexing/scaling improvements?)
