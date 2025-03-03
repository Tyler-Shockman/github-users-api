# GitHub Users API

## Welcome

This is a Spring Boot based REST API that exposes a subset of GitHub User 
data combined with their repository data. It was created as part of an 
assessment for Branch by Tyler Shockman.

## Source Directory Details

The code follows a fairly standard layout for a web service. Here are some 
descriptions of what you will find in the different directories.

- configs - Contains bean configurations for several beans that needed it.
- controllers - Contains controllers that expose the endpoints of the app.
- domain - Contains the object representation of our domain and DTOs.
- mappers - Contains mappers for mapping to and from domain objects.
- repositories - Contains data repositories for use by the services.
- services - Contains the business logic of our application.

## Requirements
For building and running this application you will need:

- <a href=https://www.oracle.com/java/technologies/downloads/#jdk23-linux>JDK 23</a>
- <a href=https://www.docker.com/products/docker-desktop/>Docker</a>

## Running Locally

### Check the application.properties
Several portions of this app can be easily configured in the application.
properties. Edit it to adjust redis connection parameters, GitHub client
parameters, rate limiting parameters, or spring doc parameters.

Alternatively, these values can be adjusted via environment variables either 
through the terminal or an IDE. Simply follow the conventions of translating 
the property to all uppercase and then replacing and "." with a "_".

### Start Redis Docker Container
This app uses redis to cache data. To start a redis docker contain run:
```bash
docker compose up -d
```
This will start the redis docker container.

### Run the Application with the Maven Wrapper

The application comes packaged with the maven wrapper, simply run:
```bash
./mvnw spring-boot:run
```
from within the application directory and the application should start up.

PERMISSION ERRORS: If you get permission errors you may need to make the 
maven wrapper to executable. You can do this by running:
```bash
chmod +x mvnw
```

## Application Docs

The application is packaged with springdoc which automatically generates 
some simple api documentation and a swagger UI. By default, you can access 
these when the application is running at "/api-docs" and "/swagger-ui" 
respectively.

## Decisions
- Validation - I decided to use spring boot validation in this project as it 
  is always important to validate input. In this case, we can lock down the 
  username quite a bit to protect ourselves by following the username rule 
  that GitHub lays out. This means usernames must be alphanumeric or dashes 
  (-), cannot contain multiple dashes in a row, cannot start or end with a 
  dash, and must be between 1, and 39 characters in length. This should be 
  fairly restrictive and a user shouldn't be able to provide any malicious 
  input.
- Rate Limiting - I decided to add a quick rate limiter to be able to 
  protect again DDOS of any kind. It is configurable in the application 
  properties.
- DTOs - I use DTOs for receiving data from external sources and for sending 
  data out. This allows us to only receive what we care about and to ensure 
  we don't send out any data we don't intend to, say if a password were 
  added to our domain GitHubUser it wouldn't be returned by the endpoint.

## Architecture
I use what I believe is a fairly standard architectural flow here.
- Presentation Layer
  - Controllers - I use these(this) to handle mapping to and from DTOs, 
    annotations around validation, annotations around error handling, and 
    some exception or non-standard flow scenarios (e.g. Not found).
  - DTOs - These define what comes in and what goes out. They can be 
    annotated with validation if needed.
  - Exception Handlers - These are a cleaner way of catching and handling a 
    myriad of exceptions.
- Domain (Service) Layer
  - Services - These get used by the controllers to perform specific domain 
    tasks. We only have a service for getting GitHubUsers and a service for 
    RateLimiting right now, but others business logic should go here.
  - Entities - Entities is a weird word and often gets associated with a DB. 
    But I think entities can be any domain based object. We have GitHubUsers 
    and GitHubRepo.
  - Mappers - These get used all over but they map things too and from the 
    domain entities. It's nice to keep a lot of that logic in one place.
- Persistence (Repository Layers)
  - Repositories - These represent and have logic for a location where data 
    is stored. In our case, data is stored at GitHub and we need to get it. 
    We have a GitHubUserRepository that has the job of getting and utilizing 
    the GitHub Rest Client to make the necessary calls to return back a 
    GitHubUser.

I hope that covers everything for this project. If you have any questions 
please reach out.  This was a lot of fun to create.