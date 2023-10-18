# SimpleLibrary-SpringBoot
This project is a simple library management system implemented using Spring Boot. It allows you to manage two entities: 
1. Authors
2. Books

## Overview
The application provides basic functionality for managing authors and books. At the moment, there is no authentication or implementation of Spring Security. You can make requests using Postman and observe the direct effect on the underlying MySQL database.

## Database
The application uses MySQL as its database. It consists of two tables:

### BOOKS
  - Columns:
    - ID: Primary key
    - NAME
    - AUTHOR_ID: Foreign key referencing the ID column of the AUTHORS table.
### AUTHORS
  - Columns:
    - ID: Primary key
    - NAME
## Design Pattern
The project follows the **Model-Repository-Service** design pattern to ensure a clear separation of concerns and maintainable code.

## Packages
The project is organized into several packages, located in the com.ister package:
1. config: Contains configuration classes, such as ```DatabaseConfig.java```, which handle the application's configuration settings.
2. controllers: Contains two controllers, each responsible for handling requests and managing the logic related to the respective entity.
3. mappers: Contains mapper classes for our entities.
4. model: Contains all the entity classes, representing the core domain objects of the application.
5. repository: Contains classes that interact with the database. These classes, such as ```AuthorRepo.java```, provide methods for performing CRUD (Create, Read, Update, Delete) operations on the entities.
6. service: Contains classes that follow the Service design pattern. They act as a layer between user requests and the database, encapsulating the business logic and providing services to the controllers.

## Postman
To interact with the application, you can import the ```LibrarySpringBoot.postman_collection.json``` file located in the "Postman Request" directory. This Postman collection provides a set of preconfigured requests that you can use to interact with the application's endpoints.

## Prerequisites
Before running the application, ensure that you have the following installed:
1. Java Development Kit (JDK)
2. MySQL database server

## Installation and Setup
1. Clone the repository to your local machine.
2. Configure the MySQL database by creating a new database and updating the database connection settings in the ```application.properties``` or ```DatabaseConfig.java``` file.
3. Build the project using your preferred build tool (Maven, Gradle, etc.).
4. Run the application.
5. Use Postman to make requests to the application's endpoints and observe the effects on the database.
