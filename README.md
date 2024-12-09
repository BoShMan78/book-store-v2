### Bookstore

### Introduction

The Bookstore project provides a simple and secure platform for managing a bookstore. It allows users with administrative privileges to edit database records while ensuring data integrity and security. The project leverages MySQL for data storage and implements JWT-based authentication for secure access control.

### Features

CRUD Operations: Full CRUD functionality for all entities, including Book, Category, User, Order, and more.
User Roles: Role-based access control with ADMIN privileges for managing the database.
Security: JWT token-based authentication and authorization.
API Documentation: Integrated Swagger for API documentation.
Database Management: Automated data initialization using Liquibase.
Unit Testing: Comprehensive unit tests with mocks to ensure code reliability.

### Technologies Used

Spring Boot
Spring Security
Hibernate
Liquibase
MySQL
Docker
Swagger

### Setup Instructions

1. Clone the repository:
   git clone <repository-url>  
    cd bookstore
2. Run the application using Docker Compose:
  docker-compose up
3. Optional: Update environment variables in the .env file for database credentials or jwt.secret to enhance security.

### API Documentation

Once the application is running, Swagger documentation can be accessed at:
http://localhost:8088/api/swagger-ui/index.html#/

### Data Initialization

The application uses Liquibase to populate the database with initial data. This process runs automatically on application startup.

### Testing

The project includes unit tests with mocks to ensure robust functionality and reliability of key components.

### Postman Collection

A Postman collection is provided in the repository. To use it:

Import the collection file (bookstore.postman_collection.json) into Postman.
Configure the environment variables in Postman for API base URL and authentication tokens.
Run the requests to interact with the API.

