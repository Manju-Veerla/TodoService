#TodoRestApi Application
Spring boot rest api application to Fetch , Create , Update, Delete Todo details

## Requirements
java 17 <br>
Maven 3 <br>
Springboot 3.1.4

## Running the application locally
To run the application :
first database connectivity changes to be done in application.properties file

Creation of database tables can be done using Hibernate or liquibase

Hibernate - Uncomment the spring.jpa.hibernate.ddl-auto=create in application.properties <br>
Liquibase - Change spring.liquibase.enabled=false to spring.liquibase.enabled=true in application.properties

## Deploying the application in server

To create a jar file for deploying the application : Run the command mvn clean install

## Rest API documentation 

http://localhost:8080/swagger-ui/index.html#

##Postman Collection
LeanX.postman_collection.json is available in path TodoRestAPI\src\main\resources\.postman



