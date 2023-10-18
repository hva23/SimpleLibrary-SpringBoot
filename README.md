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
    - ID<br>
    - NAME<br>
    - AUTHOR_ID

- **AUTHORS**
  - Columns :
    - ID<br>
    - NAME<br>

### Design pattern
model, repository, service

