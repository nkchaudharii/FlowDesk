# FlowDesk

FlowDesk is a production-style backend for a collaborative workspace and project management platform. It is designed as a flagship portfolio project for graduate backend engineering roles and demonstrates a realistic SaaS-style architecture with Spring Boot, Spring Security, JWT authentication, PostgreSQL, Flyway, Docker, OpenAPI, and testing.

## Overview

FlowDesk provides:
- Workspace creation and ownership management
- Project organization inside workspaces
- Task creation, filtering, and prioritization
- Comments and notifications
- Secure authentication and role-based authorization
- PostgreSQL persistence with Flyway migrations
- RESTful APIs documented with Swagger/OpenAPI

## Features

- Authentication and authorization with JWT
- Registration, login, refresh, and logout flows
- User profile endpoint
- Workspace and project management
- Task CRUD with filtering and pagination
- Comments and notifications
- Global exception handling and validation
- Health check endpoint via Spring Boot Actuator
- Docker support for local deployment

## Architecture

The project follows a clean separation of concerns:
- config
- security
- controller
- service
- service.impl
- repository
- entity
- dto
- mapper
- exception
- validation

## Technology Stack

- Java 17
- Spring Boot 3.2.x
- Spring Security
- Spring Data JPA
- PostgreSQL
- Flyway
- Maven
- JWT
- Docker Compose
- Swagger/OpenAPI
- JUnit 5
- Mockito

## Running Locally

1. Ensure Java 17 and Maven are installed.
2. Start PostgreSQL locally or use Docker Compose.
3. Create a database named `flowdesk`.
4. Set environment variables if needed.
5. Run:

```bash
mvn spring-boot:run
```

The API will be available at:
- http://localhost:8080/api/v1
- http://localhost:8080/swagger-ui.html
- http://localhost:8080/actuator/health

## Running with Docker

```bash
docker compose up --build
```

## Configuration

The application uses the following environment variables:
- DB_HOST
- DB_PORT
- DB_NAME
- DB_USERNAME
- DB_PASSWORD
- JWT_SECRET
- JWT_ACCESS_TOKEN_TTL
- JWT_REFRESH_TOKEN_TTL

## API Documentation

Swagger UI is available at:
- http://localhost:8080/swagger-ui.html

OpenAPI JSON is available at:
- http://localhost:8080/v3/api-docs

## Testing

Run tests with:

```bash
mvn test
```

## Future Improvements

- Add workspace member invitations and invitations workflow
- Add file upload and storage integration
- Add audit trail visibility and admin console features
- Add email notifications and password reset flow
- Add end-to-end testing with Testcontainers
