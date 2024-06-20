# Product Service

The Product Service is a Spring Boot application designed to manage and retrieve product information.

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Local Setup](#local-setup)
- [Usage](#usage)
    - [Endpoints](#endpoints)
    - [Examples](#examples)
- [Testing](#testing)

## Features

- Add, retrieve products
- Search products by category with pagination and sorting
- Exception handling for invalid requests

## Technologies

- Java 17
- Spring Boot 3.3.0
- Spring Data JPA
- H2 Database
- Lombok
- JUnit 5 and Mockito (for testing)

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven
- IDE (IntelliJ IDEA, Eclipse, etc.)


## Local Setup

- Clone the repository.
- Import the project into the IDE of your choice.
- Let the maven project update.
- Run the application as a spring boot application

## Usage

### Endpoints

The service exposes the following REST endpoints:

- `POST /v1/product`: Add a new product.
- `GET /v1/product`: Search products by category with pagination.

### Examples: Postman Curls

#### Add a Product 

```bash
curl --location 'http://localhost:35795/v1/product' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Red Shirt",
    "description": "Red hugo boss shirt",
    "brand": "Hugo Boss",
    "tags": [
        "olive",
        "shirt",
        "slim fit"
    ],
    "category": "apparel"
}'
```

### Search a Product By Category

```bash
curl --location 'http://localhost:8080/v1/product?category=apparel'
```

### Testing

Run tests using Maven:

```bash
mvn test
```
