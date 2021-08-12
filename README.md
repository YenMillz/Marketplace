# Marketplace

REST API for managing products on a marketplace.

A RESTful API created using Spring boot. A Marketplace that allows
to register a new user or login with created one and to manage products.

## Built With

- [Spring boot](https://spring.io/projects/spring-boot/)

- [Spring security](https://spring.io/projects/spring-security/)

- [PostgreSQL](https://www.postgresql.org/)

- [MyBatis](https://mybatis.org/mybatis-3/)

- [OpenApi 3](https://springdoc.org//)

- [FlyWay](https://flywaydb.org//)

## Getting Started

Marketplace is just a simple platform where people
regularly gather for the purchase and sale of goods. 
It uses a relational database (PostgreSQL) to store the data. 
To secure endpoints, I've used bearer key provided by 
jjwt library, and configured with swagger3. MyBatis was used to
perform CRUD operation with database. At the last, FlyWay guarantees
database migration to future versions.
All APIs are "self-documented" by OpenApi3 using annotations.

### Prerequisites

Requirements for the software and other tools to build, test and push
- [Java 8](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)
- [Maven 3](https://maven.apache.org/mailing-lists.html)

### Setup and Installing

**1. Clone the repo from GitHub**
   
    git clone https://github.com/...

**2. Spin-up PostgreSQL database instance**

Download from [here](https://www.postgresql.org/) and install 
locally on the machine. Then create database.
     
    create database marketplace

**3. Update database configurations in applications.properties** 

If your database is hosted at some cloud platform or if you
have modified the SQL script file with some different
username and password, update src/main/resources/application.properties
file accordingly:

    spring.datasource.url=jdbc:postgresql://localhost:5432/marketplace
    spring.datasource.username=postgres
    spring.datasource.password=postgres    
     
End with an example of getting some data out of the system or using it
for a little demo

**4. Build and run the spring boot application**

    mvn package
    java -jar target/marketplace-0.0.1-SNAPSHOT.jar

Alternatively, you can run the app without packaging it using -

    ./mvnw spring-boot:run

The app will start running at http://localhost:8080

## Explore Rest APIs

---

The app defines the following CRUD APIs

### Authentication

Method | Url | Description | Sample Valid Request Body
--- | --- | --- | --- |
POST | /authentication/login | Login an existing user | JSON
POST | /authentication/refresh | Generate new refresh token | JSON
POST | /authentication/register | Create a new user | JSON

### All Products

Method | Url | Description | Sample Valid Request Body
--- | --- | --- | --- |
GET | /all-products | Find all products |

### Products

Method | Url | Description | Sample Valid Request Body
--- | --- | --- | --- |
GET | /products | Find all products (For logged in user)
POST | /products | Create product (For logged in user) | JSON
GET | /products/{id} | Find product (For logged in user) by id
PUT | /products | Update product (For logged in user) | JSON
DELETE | /products/{id} | Delete product (For logged in user)
PATCH | /products/{id}/like | Like a product (For logged in user)
PATCH | /products/{id}/unlike | Unlike a product (For logged in user)

Test them using swagger at http://localhost:8080/swagger-ui.html
or any other rest client.

## Sample Valid JSON Request Body

---

**Registration:** /registration

    {
          "username": "YenMillz",
          "email": "yen.millz@gmail.com",
          "password": "Password1!",
    }

**Create product:** /products

    {
          "title": "Toaster Royal Bread v3",
          "price": "299.99",
          "description": "blah blah blah blah",
    }

**Update product:** /products/{id}

    {
          "title": "Toaster Royal Bread v3",
          "price": "99.99",
          "description": "woah woah woah woah",
    }

## Authors

- **Bivol Gavril**
