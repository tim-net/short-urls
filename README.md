# Short url application
Short url application is used for creating short urls from long ones. It has REST API for doing it. In order to create a short url call REST API  with address /api/urls using PUT method and add longUrl parameter. Response will contain a created short url which can be used to be redirected to the long url. Additionaly the application gathers statistics of redirection event which can be accessed by calling REST API with address /api/redirect-statistics/summary and adding a code. The code is the part of a short url after a schema, hostname and port - a path. for example in the URL such as http://google.by/fTdkov6 the code (path) is fTdkov6. The redirection statics returned contains the code, the correspondent long url, the date of the first redirection, the date of a last redirection and the number of redirection events for this short url.

# Installation
To launch the application in Linux:
```sh
gradlew bootRun
```
In Windows:
```sh
gradlew.bat bootRun
```
The application may be run in  a docker container, Dockerfile for creation of an image is located in root folder. Example of creation and launching with exposing http port to 8080:
```sh
docker build -t shorturls .
docker run -p 8080:80 shorturls
```
After that you will be able to send REST requests to your local 8080 port. This should be normally:
http://localhost:8080/api/urls and http://localhost:8080/api/redirect-statistics/summary
