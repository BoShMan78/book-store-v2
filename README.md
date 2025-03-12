# 📚 Bookstore

## 🚀 Introduction

The Bookstore project provides a simple and secure platform for managing a bookstore. It allows users with administrative privileges to edit database records while ensuring data integrity and security. The project leverages MySQL for data storage and implements JWT-based authentication for secure access control.

## 📝 Features

+ **CRUD Operations:** Full CRUD functionality for all entities, including Book, Category, User, Order, and more.
+ **User Roles:** Role-based access control with ADMIN privileges for managing the database.
+ **Security:** JWT token-based authentication and authorization.
+ **API Documentation:** Integrated Swagger for API documentation.
+ **Database Management:** Automated data initialization using Liquibase.
+ **Unit Testing:** unit tests with mocks to ensure code reliability.

## 💻 Technologies Used

+ Java 17
+ Spring Boot 3.2.4
+ Spring Security
+ MapStruct 1.5.5.Final
+ Hibernate
+ Liquibase
+ MySQL 8.2.0
+ Docker
+ Swagger (springdoc-openapi 2.5.0)
+ JJWT 0.12.6
+ Testcontainers 1.20.3

### 💾 Setup Instructions

1.  **Clone the repository:**
    ```bash
    git clone <repository-url>
    cd bookstore
    ```
2.  **Build the application:**
    ```bash
    mvn clean package
    ```
    * This step compiles the Java code and packages it into an executable JAR file.
    
3.  **Run the application using Docker Compose:**
    ```bash
    docker-compose up --build
    ```
    * The `--build` flag ensures that the Docker image is built if it doesn't exist or if the Dockerfile has changed.
    
4.  **Optional: Update environment variables:**
    You can modify the `.env` file to customize database credentials or the `jwt.secret` for enhanced security.
    .env Example
    ```bash
    MYSQLDB_ROOT_PASSWORD=1234qwer
    MYSQLDB_DATABASE=book_store
    MYSQLDB_USER=root
    MYSQLDB_PASSWORD=1234qwer
    MYSQLDB_LOCAL_PORT=3306
    MYSQLDB_DOCKER_PORT=3306
    
    SPRING_LOCAL_PORT=8088
    SPRING_DOCKER_PORT=8080
    DEBUG_PORT=5005
    
    JWT_EXPIRATION_TIME=3000000
    JWT_SECRET=thisissecretphrasethatshouldbelonganoughtoprovidesecuredata

## Test Users

For convenient application testing, two users are registered by default:

* **Administrator:**
    * Email: `admin1@example.com`
    * Password: `Password#1`
    * Role: `ADMIN` (can perform CRUD operations with books, categories, and orders)
* **Regular User:**
    * Email: `user@example.com`
    * Password: `Password#2`
    * Role: `USER` (can view books, categories, place orders, and manage their shopping cart)

**Attention:** These accounts are for testing purposes only. Their use in a production environment is not recommended.

## Password Requirements

To ensure security, the password must meet the following requirements:

* Minimum 8 characters.
* Contain at least one lowercase letter (a-z).
* Contain at least one uppercase letter (A-Z).
* Contain at least one digit (0-9).
* Contain at least one special character (#$@!%&*?).

Example of a valid password: `StrongPass#1`

## 📃 API Documentation

Once the application is running, Swagger documentation can be accessed at:

http://localhost:8088/api/swagger-ui/index.html#/

## 🏎 Data Initialization

The application uses Liquibase to populate the database with initial data. This process runs automatically on application startup.

## 🔨 Testing

The project includes unit tests with mocks to ensure robust functionality and reliability of key components.

## ✉️ Postman Collection

A Postman collections is provided in the repository. To use it:
docs/*.postman_collection.json

Import the collection files (*.postman_collection.json) into Postman.
Configure the environment variables in Postman for API base URL and authentication tokens.
Run the requests to interact with the API.

## 🌐 API Endpoints

Here's a brief overview of key API endpoints:

* **Book management:**
    * `GET /api/books`: Retrieve all books (Authenticated).
    * `GET /api/books/{id}`: Retrieve a book by ID (Authenticated).
    * `GET /api/books/search`: Search book by title and author (Authenticated).
    * `POST /api/books`: Create a new book (ADMIN).
    * `PUT /api/books/{id}`: Update a book (ADMIN).    
    * `DELETE /api/books/{id}`: Delete a book (ADMIN).
* **Categories management:**
    * `GET /api/categories`: Retrieve all categories (Authenticated).
    * `GET /api/categories/{id}`: Retrieve a category by ID (Authenticated).
    * `GET /api/categories/{id}/books`: Retrieve books by category ID (Authenticated).
    * `POST /api/categories`: Create a new category (ADMIN).
    * `PUT /api/categories/{id}`: Update a category (ADMIN).
    * `DELETE /api/categories/{id}`: Delete a category (ADMIN).
* **Shopping Cart management:**
    * `GET /api/cart`: Get information about shopping cart (USER).
    * `POST /api/cart`: Add a book to the shopping cart (USER).
    * `PUT /api/cart/items/{cartItemId}`: Update cart item (USER).
    * `DELETE /api/cart/items/{cartItemId}`: Delete cart item (USER).
* **Order management:**
    * `GET /api/orders`: Retrieve all orders (USER).
    * `GET /api/orders/{orderId}/items`: Retrieve information about items by order id (USER).
    * `GET /api/orders/{orderId}/items/{itemId}`: Retrieve information about specific item by order id and item id (USER).
    * `POST /api/orders`: Place a new order (USER).
    * `PATCH /api/orders/{orderId}`: Change order status by order id (ADMIN).
* **Authentication:**
  * `POST /api/auth/login`: User login (Public).
  * `POST /api/auth/registration`: New user registration (Public).

## Video Demonstration

For a quick overview of the application's functionality, please watch the following video demonstration:

[Short Demonstration Video](https://vimeo.com/manage/videos/1065013803)

This video provides a brief walkthrough of the key features and demonstrates how to use the application.

## 🏗️ Component/Entity Diagram

```mermaid
erDiagram
    BOOK {
        Long id PK
        String title
        String author
        BigDecimal price
        String isbn
        String description
        String coverImage
        boolean isDeleted
    }
    CATEGORY {
        Long id PK
        String name
        String description
        boolean isDeleted
    }
    CART_ITEM {
        Long id PK
        Long cart_id FK
        Long book_id FK
        int quantity
    }
    ORDER {
        Long id PK
        Long user_id FK
        String status
        BigDecimal total
        LocalDateTime orderDate
        String shippingAddress
        boolean isDeleted
    }
    ORDER_ITEM {
        Long id PK
        Long order_id FK
        Long book_id FK
        int quantity
        BigDecimal price
    }
    ROLE {
        Long id PK
        String name
    }
    SHOPPING_CART {
        Long id PK
        Long user_id FK
        boolean isDeleted
    }
    USER {
        Long id PK
        String email
        String password
        String firstName
        String lastName
        String shippingAddress
    }
    BOOK ||--o{ CATEGORY : contains
    CART_ITEM }|--|| SHOPPING_CART : contains
    CART_ITEM }|--|| BOOK : contains
    ORDER }|--|| USER : places
    ORDER_ITEM }|--|| ORDER : contains
    ORDER_ITEM }|--|| BOOK : contains
    USER ||--o{ ROLE : has
    SHOPPING_CART }|--|| USER : belongs

