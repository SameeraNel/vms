# vms
Visitor Management System

## Developer Setup

### Prerequisites

*   Java 17
*   Maven 3.8+

### Building and Running the Application

To build and run the application, execute the following command from the root of the project:

```bash
mvn spring-boot:run
```

## API Documentation

The OpenAPI (Swagger) documentation is available at `/swagger-ui.html` once the application is running.

## Postman Collection

A Postman collection with sample requests for all major API flows is available in the root of the repository: `vms-backend.postman_collection.json`.

## Running with Docker Compose

To build and run the application using Docker Compose, execute the following command from the root of the project:

```bash
docker-compose up --build
```
