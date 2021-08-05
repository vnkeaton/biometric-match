# biometric-match
IDSL project - java rest api matching service

Hello-  Thank you for the opportunity to participate in the SAIC Identity and Data Sciences Laboratory Code Assessment.

This is a basic CRUD Spring boot REST api.  Howver, I am currently missing Update and Delete operations and hope to be able to swing back aournd to take care of those.
Please note there is swagger documentation: http://localhost:8080/swagger-ui.html

The REST layers are controller, service, and repository where controller houses the api endpoint.
The function of interest is the POST operation where the body accepts an array of image files,  called "files".  
The method will compare the first 2 files in the array.
The files are uploaded to an H2 (in memory) database just to demonstrate the service layer of the REST application 
with an entitiy model created in H2 as the repository or persisted layer.

The service method uses a random number generator to similate a matching score by comparing two png files and returns the score along with the two files names.

The application is configured to create a docker image.

TO RUN: 
The build-java sh ($chmod +x build-java) will build the REST application as biometric-match-0.0.1-SNAPSHOT.jar.
The build-docker sh ($chmod +x build-docker) will build an image, tag, push, and finally run the application based on the Dockerfile provided.

I used PostMan for testing.

POST http://localhost:8080/biometric/match?=files where the body contains:
key=files, value=1.png
key=files, value=2.png
(Note, key type is "file".)

GET http://localhost:8080/biometric/downloadFile/{fileName} will simply retrieve any file posted from the database to display.
(example: http://localhost:8080/biometric/downloadFile/1.png)

This application will be consumed by the Go application that retrieves images and compares each one onto the other.

I look forward to discussing this application with you.

Thanks-
Viki
