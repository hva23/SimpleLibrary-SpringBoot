# SimpleLibrary-SpringBoot
This project is a simple library management system implemented using Spring Boot. It allows you to manage two entities: 
1. User
2. Book

## Table of Contents

- [Overview](#overview)
- [Database](#database)
  - [BOOKS Table](#books)
  - [USERS Table](#users)
  - [Database DDL](#database-ddl)
  - [Database Diagram](#database-diagram)
- [Design Pattern](#design-pattern)
- [Packages](#packages)
- [Postman](#postman)
- [Installation and Setup](#installation-and-setup)

## Overview
This application is developed using the Spring Boot framework. The application allows users to manage books and authors in a library system. It provides a RESTful API for performing CRUD (Create, Read, Update, Delete) operations on the entities.

## Database
The application uses MySQL as its database. It consists of two tables:

### BOOKS
  - Columns:
    - ID: Primary key
    - NAME
    - AUTHOR_ID: Foreign key referencing the ID column of the USERS table.
### USERS
  - Columns:
    - ID: Primary key
    - NAME: Unique key
    - PASSWORD
    - ROLE: It is determined using the Roles enumeration values.
    - ENABLED: Added for implementing Spring Security JDBC authentication.<br>
    
### Database DDL
Use this code block to create database and tables:
```SQL
CREATE DATABASE LIBRARY;
USE LIBRARY;
CREATE TABLE BOOKS
(
    ID int not null primary key,
    NAME varchar(50) not null,
    AUTHOR_ID varchar(50) not null,
    constraint books_fk foreign key (AUTHOR_ID) references users (ID)
);

CREATE TABLE USERS
(
    ID varchar(50) not null primary key,
    NAME varchar(50) not null,
    PASSWORD varchar(50) not null,
    ROLE varchar(15) not null,
    ENABLED tinyint(1) not null,
    constraint users_uk unique (NAME)
);
```
### Database Diagram
<p align="center">
  <img src="https://github.com/hva23/SimpleLibrary-SpringBoot/blob/master/images/simplelibrary-springboot-tables.png?raw=true" alt="Database Diagram">
</p>

## Design Pattern
The project follows the **Model-Repository-Service** design pattern to ensure a clear separation of concerns and maintainable code.

## Packages
The project is organized into several packages, located in the com.ister package:
1. common: Contains common components like Roles enumeration.
2. config: Contains configuration classes, such as ```DatabaseConfig.java```, which handle the application's configuration settings.
4. controllers: Contains two controllers, each responsible for handling requests and managing the logic related to the respective entity.
5. filter: Contains two filters. The EveryUrlFilter.java filter inspects all requests and is executed for each request. The UserFilter.java filter is associated with a specific address and is only executed when we request that particular address.
6. mappers: Contains mapper classes for our entities.
7. model: Contains all the entity classes, representing the core domain objects of the application.
8. repository: Contains classes that interact with the database. These classes, such as ```UserRepo.java```, provide methods for performing CRUD (Create, Read, Update, Delete) operations on the entities. we use JdbcTemplate to interact with database.
9. service: Contains classes that follow the Service design pattern. They act as a layer between user requests and the database, encapsulating the business logic and providing services to the controllers.

## Postman
To interact with the application, you can import the ```LibrarySpringBoot.postman_collection.json``` file located in the "Postman Request" directory. This Postman collection provides a set of preconfigured requests that you can use to interact with the application's endpoints.

## Installation and Setup
1. Clone the repository to your local machine.
2. Configure the MySQL database by creating a new database and updating the database connection settings in the ```application.properties``` or ```DatabaseConfig.java``` file.
3. Create required tables according to [this](#database) section.
4. Build the project using your preferred build tool (Maven, Gradle, etc.).
5. Run the application.
6. Use Postman to make requests to the application's endpoints and observe the effects on the database.
