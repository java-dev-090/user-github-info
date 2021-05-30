# Project Overview
This application is built in spring boot. It is used to get the user's repositories information from GitHub which are not fork. The information consist of 
- Repository Name
- Owner Name
- Branch Name with Last Commit SHA. 

## Requirement
For building and running the application user need:
- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

## Running the application locally
1. There are several ways to run this application on your local machine. One way is to execute the main method in the <b>com.tui.github.UserGithubInfoApplication</b> class from your IDE.
2. If <b>maven</b> is installed in the system then user can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so: 
```shell
mvn spring-boot:run
```
3. Alternatively, user can use the maven wrapper class of application
```shell
./mvnw spring-boot:run
```
4. I have provided the jar file, in jar folder. User can use that to run the application.
```
java -jar github-0.0.1-SNAPSHOT.jar
```
## Test the application locally
Once the application started, user can test the application in following ways:
1. Swagger-UI (http://localhost:8080/swagger-ui.html)
2. Using Postman
3. Using CURL

The service request and their responses are as below:
1. When user provide the existing GitHub user:

```
curl -X 'GET' 'http://localhost:8080/user/repos/v1/test' -H 'accept: application/json'
```
The request response is : 
```
[
  {
    "name": "HelloWorld",
    "ownerLogin": "test",
    "branch": [
      {
        "name": "asd",
        "sha": "ec4ab42080e536cdfd628fc858b6d53992a90ef0"
      },
      {
        "name": "master",
        "sha": "c8e8f1b026c4840fed7376d33786f2c721375ae6"
      }
    ]
  },
  {
    "name": "rokehan",
    "ownerLogin": "test",
    "branch": [
      {
        "name": "master",
        "sha": "54a889cb5438d13c92850edb8e25a793ccec42aa"
      }
    ]
  },
  {
    "name": "SDWebImage",
    "ownerLogin": "test",
    "branch": [
      {
        "name": "2.0-compat",
        "sha": "95ef42d48c979223b3414c5a50ebee40659c9663"
      },
      {
        "name": "master",
        "sha": "8b37b16f4583bc8b8c5e12b68f1eb10d198528a2"
      },
      {
        "name": "refresh-cached-pull-request-326",
        "sha": "eed78e37e8664ded1047b1261244cf30a67356e5"
      }
    ]
  },
  {
    "name": "sNews",
    "ownerLogin": "test",
    "branch": []
  },
  {
    "name": "Test--01",
    "ownerLogin": "test",
    "branch": [
      {
        "name": "master",
        "sha": "60c3b002b742176770fa742ceab323e731966b9a"
      }
    ]
  }
]
```
2. When user provide the non-existing GitHub user:
```
curl -X 'GET' 'http://localhost:8080/user/repos/v1/test87656743' -H 'accept: application/json'
```
The request response is: 
```
{
  "status": 404,
  "message": "Invalid User: Provided userName is not a GitHub user"
}
```
3. When user provide the application/xml in the header:
```
curl -X 'GET' 'http://localhost:8080/user/repos/v1/test' -H 'accept: application/xml'
```
The request response is: 
```
{
  "status": 406,
  "message": "Invalid Accept Header: Only application/json is accepted by application."
}
```

## API Specification
Please refer <b>github-swagger.yaml</b>
