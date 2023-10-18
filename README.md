# SimpleLibrary-SpringBoot
Implementing a library in the simplest form possible, we have two entities:
1. Author
2. Book

Currently, there is no authentication or implementation of Spring Boot Security.<br>
You can make requests using Postman and observe the direct effect on your database.
## Database
In this project, we are using MySQL as our database.
### Tables
- **BOOKS**
  - Columns :
    - ID : Primary key<br>
    - NAME<br>
    - AUTHOR_ID : Foreign key referencing to ID column of AUTHORS table.

- **AUTHORS**
  - Columns :
    - ID : Primary key<br>
    - NAME<br>

### Design pattern
model, repository, service
### Packages



1. config: This package contains configuration classes, such as ```DatabaseConfig.java```, which handle the configuration settings for your application.
2. controllers: This package contains two controllers, each responsible for handling requests and managing the logic related to the corresponding entity.
3. mappers : Mapper classes for out entities.
4. model: The ```model``` package is where all the entities are located. It is also commonly referred to as the "domain" package, as it represents the core domain objects of your application.
5. repository: The ```repository``` package contains classes that interact with the database. These classes, such as ```AuthorRepo.java```, typically provide methods for performing CRUD (Create, Read, Update, Delete) operations on the corresponding entities.
6. service: The ```service``` package follows the Service design pattern. It acts as a layer between user requests and the database. The classes in this package encapsulate the business logic and provide services to the controllers. They interact with repositories to perform operations on the entities.
All packages are in com.ister
