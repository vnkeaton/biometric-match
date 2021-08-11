# biometric-match
IDSL project - java rest api matching service

This is a basic CRUD Spring boot REST api.  Please note there is swagger documentation: http://localhost:8080/swagger-ui.html

The REST layers are controller, service, and repository where controller houses the api endpoints.
The function of interest is the POST operation for ~/biometric/image/match where the body accepts an array of image files, "files".  
The operation will compare the first 2 files in the array utilizing a randome number generator and return a match score between 0-10
The files are uploaded to an H2 (in memory) database.  This allows a client to consume all match scores once match posting is complete.  The match score information endpoint is ~/biometric/matchscore/downloadFile/all endpoint.

When running locally, the H2 database is viewed via http://localhost:8080/h2-console 

The application is configured to create a docker image.

TO RUN: 
The build-java sh ($chmod +x build-java) will build the REST application as biometric-match-0.0.1-SNAPSHOT.jar.
The build-docker sh ($chmod +x build-docker) will build a docker image and runs the application in the docker container.
The clg sh ($chmod +x clg) will build the REST application, build the docker image, and run the docker container.  (this is the one to run.)

I used PostMan for testing.  (see post json exported file: biometric-match-api.postman_collection.json)

POST http://localhost:8080/biometric/match?=files where the body contains:
key=files, value=1.png
key=files, value=2.png
(Note, key type is "file".)

GET http://localhost:8080/biometric/downloadFile/{fileName} will simply retrieve any file posted from the database to display.
(example: http://localhost:8080/biometric/downloadFile/1.png)

This REST app will be consumed by the match-run Go application utilizing the biometric-match-client.
