# Legimus Online Book Shop Backend

Backend API for an online bookshop. The application covers account management,
book catalog browsing, category management, shopping cart operations, order
placement, admin/user management, email-based account flows, and API
documentation through Swagger/OpenAPI.

## Core functionality

- Authentication and account flows:
  - user registration and login;
  - password reset and password change;
  - email confirmation/change success flows;
  - JWT-based access and refresh cookies.
- User management:
  - current user profile lookup and update;
  - user list lookup;
  - user account enable/disable actions;
  - role update actions.
- Book catalog:
  - list, search, create, update, and delete books;
  - retrieve individual books;
  - pagination and sorting support.
- Categories:
  - list, create, update, and delete categories;
  - retrieve books by category.
- Shopping cart:
  - view cart contents;
  - add items;
  - update item quantity;
  - remove items.
- Orders:
  - place orders;
  - list orders;
  - update order status;
  - inspect order items.

## Technology stack

- Java 21
- Spring Boot 3.4.4
- Spring Web MVC
- Spring Security
- Spring Data JPA / Hibernate
- MySQL 8.4 locally through Docker Compose
- Liquibase
- Maven
- Lombok
- MapStruct
- Bean Validation
- Springdoc OpenAPI / Swagger UI
- JWT (`jjwt`)
- Resend Java SDK
- Testcontainers
- Docker / Docker Compose

## API documentation

Swagger UI is available locally at:

- <http://localhost:8080/swagger-ui/index.html>

OpenAPI JSON is available locally at:

- <http://localhost:8080/v3/api-docs>

The local environment uses dummy secrets by default. It is intended for local
startup, schema validation, Swagger exploration, and non-email development
flows.

Email delivery requires a real Resend API key and sender email. The application
can still start locally with dummy values, but email-dependent flows will not
send real messages until those values are replaced.

## Local startup

See [RUN_ME.md](RUN_ME.md) for the exact local Docker commands.

Short version:

```bash
docker-compose --env-file .env.sample up --build
```

Backend URL:

```text
http://localhost:8080
```

## License

This project is proprietary. Copyright and all rights are reserved by Adam
Mudrak, resident of Ukraine.

See [LICENSE.md](LICENSE.md) for permitted educational use and usage restrictions.
