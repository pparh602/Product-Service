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
- `POST /v1/orders/complete`: Add Completed Order
- `POST /v1/orders/pending`: Add Pending Order
- `GET /v1/orders/combined`: Get combined result from Pending and Completed Order with pagination


### Get Combined Orders

**Endpoint:** `/v1/orders/combined`

**Method:** `GET`

**Description:** Retrieves a combined list of pending and completed orders, with pagination and sorting, filter by startDate and endDate.

**Query Parameters:**
- `page`: (optional) Page number (default is 0).
- `size`: (optional) Number of records per page (default is 10).
- `sortField`: (optional) Sorting by field name eg amount, orderDate etc.
- `isSortDesc`: (optional) Sorting by ASC or DESC order.
- `startDate`: (required) Filters Order by Start Date (yyyy-MM-dd) 
- `endDate`: (required) Filters Order by End Date (yyyy-MM-dd) 


**Curl Request:**
```bash
curl --location 'http://localhost:8080/v1/orders/combined?startDate=2023-01-21&endDate=2025-01-01&page=0&size=10&sortField=amount&isSortDesc=true'
```

### Search a Product By Category

**Endpoint:** `/v1/product`

**Method:** `GET`

**Description:** Retrieves a list of products, with pagination.

**Curl Request:**
```bash
curl --location 'http://localhost:8080/v1/product?category=apparel&page=0&size=5'
```

### Add Product

**Endpoint:** `/v1/product`

**Method:** `POST`

**Description:** Add Product.

**Curl Request:**
```bash
curl --location 'http://localhost:8080/v1/product' \
--header 'Content-Type: application/json' \
--data '{
    "name": "plum Shirt",
    "description": "yellow hugo boss shirt",
    "brand": "O'\''Reilly Group",
    "tags": [
        "sky blue",
        "shirt",
        "slim fit"
    ],
    "category": "apparel"
}'
```

### Add Complete Order

**Endpoint:** `/v1/orders/complete`

**Method:** `POST`

**Description:** Add Complete Order.

**Curl Request:**
```bash
curl --location 'http://localhost:8080/v1/orders/complete' \
--header 'Content-Type: application/json' \
--data '{
    "quantity": "10",
    "productName": "Rustic Steel Pizza",
    "orderDate": "2024-02-21T10:15:30Z",
    "orderStatus": "Completed",
    "amount": 77
}'
```

### Add Pending Order

**Endpoint:** `/v1/orders/pending`

**Method:** `POST`

**Description:** Add Pending Order.

**Curl Request:**
```bash
curl --location 'http://localhost:8080/v1/orders/pending' \
--header 'Content-Type: application/json' \
--data '{
    "quantity": "6",
    "productName": "Small Wooden Chicken",
    "orderDate": "2024-02-21T10:15:30Z",
    "orderStatus": "Completed",
    "amount": 626
}'
```

### Testing

Run tests using Maven:

```bash
mvn test
```
