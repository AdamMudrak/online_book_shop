# Legimus OnlineBookShopApp

## 📌 Introduction
Legimus is a modern and user-friendly online bookshop that allows customers to easily browse,
search, and purchase books across various categories. With features like a shopping cart,
order management, and detailed book listings, Legimus offers a seamless experience for book
lovers looking to explore new titles or revisit old favorites.

## 🚀 Features

## Authentication API Controller

- **POST**: `/auth/change-password` - Change password while being logged in, either using a random or your own password.
- **POST**: `/auth/register` - Register a new user in the app.
- **POST**: `/auth/login` - Log in using existing email account.
- **POST**: `/auth/forgot-password` - Initiate password reset via a link sent to your email.
- [AuthenticationController](src/main/java/com/example/onlinebookshop/controllers/AuthController.java)

## User API Controller

- **GET**: `/users` - Retrieve all users.
- **POST**: `/users/change-user-account-status/{userId}` - Enable or disable user account.
- **GET**: `/users/me` - Retrieve profile info.
- **PUT**: `/users/me` - Update profile info.
- **PUT**: `/users/{employeeId}/role` - Update user role.
- [UserController](src/main/java/com/example/onlinebookshop/controllers/UserController.java)

## Books API Controller

- **GET**: `/books/{id}` - Get a book by id.
- **DELETE**: `/books/{id}` - Delete an existing book.
- **PUT**: `/books/{id}` - Update an existing book.
- **GET**: `/books/search` - Search book by params.
- **POST**: `/books` - Create a new book.
- **GET**: `/books` - Get all books optionally with pagination and sorting.
- [BookController](src/main/java/com/example/onlinebookshop/controllers/BookController.java)

## Categories API Controller

- **POST**: `/categories` - Create a new category.
- **GET**: `/categories` - Get all categories optionally with pagination and sorting.
- **GET**: `/categories/{id}/books` - Get all books by category id.
- **GET**: `/categories/{id}` - Get a category by id.
- **DELETE**: `/categories/{id}` - Delete an existing category.
- **PUT**: `/categories/{id}` - Update an existing category.
- [CategoryController](src/main/java/com/example/onlinebookshop/controllers/CategoryController.java)

## Shopping cart API Controller

- **POST**: `/cart` - Add item to the shopping cart.
- **GET**: `/cart` - Get all items in the shopping cart.
- **DELETE**: `/cart/cart-items/{cartItemId}` - Delete item from the shopping cart.
- **PUT**: `/cart/cart-items/{cartItemId}` - Update item in the shopping cart.
- [ShoppingCartController](src/main/java/com/example/onlinebookshop/controllers/ShoppingCartController.java)

## Orders API Controller

- **GET**: `/orders/{orderId}/items/{itemId}` - Get an item by specific order id and item id.
- **PATCH**: `/orders/{orderId}` - Update the existing order status.
- **GET**: `/orders/{orderId}/items` - Get items by specific order id.
- **POST**: `/orders` - Add a new order.
- **GET**: `/orders` - Get all orders.
- [OrderController](src/main/java/com/example/onlinebookshop/controllers/OrderController.java)

### ⚠️Nota bene!
#### All requests above, except for:
- **POST**: `/auth/register`
- **POST**: `/auth/login`
- **POST**: `/auth/forgot-password`

- **GET**: `/books/{id}`
- **GET**: `/books/search`
- **GET**: `/books`

- **GET**: `/categories`
- **GET**: `/categories/{id}/books`
- **GET**: `/categories/{id}`
#### require user to be **AUTHENTICATED**. The auth tokens will be automatically saved to cookies when you use:
- **POST**: `/auth/login`

## 🛠️ Technologies Used

**Legimus OnlineBookShopApp** uses a modern, modular, and scalable tech stack centered on **Java 21**
and the **Spring framework** to ensure strong functionality, secure processes, and efficient deployment.

## 🧱 Core Language and Environment

- **Java 21** – Leverages the latest language features and performance improvements.

## ⚙️ Backend Technologies

- **Spring Boot 3.4.4** – Main framework for application development, configuration, and deployment.
- **Spring Security** – Provides secure authentication and authorization mechanisms.
- **Spring Data JPA** – Simplifies data access with Hibernate and ORM support.
- **Spring Web** – Handles RESTful APIs and HTTP requests.
- **Spring Validation (Hibernate Validator)** – Ensures input and data integrity using annotations.

## 🗃️ Persistence and Migrations

- **MySQL 8.0.33** – Reliable, high-performance relational database.
- **Liquibase 4.31.1** – Manages versioned database schema migrations.

## 🔐 Authentication and Authorization

- **JWT (JJWT 0.12.6)** – Token-based authentication with runtime and API components.

## 📬 Communication and Notifications

- **Resend Java SDK 4.3.0** – Sends email notifications for actions like registration and password reset.

## 🔧 Development Tools & Utilities

- **Lombok 1.18.36** – Reduces boilerplate code for model and utility classes.
- **MapStruct 1.6.3** – Generates type-safe mappers for DTO ↔ entity conversion.
- **Lombok-MapStruct Binding 0.2.0** – Ensures compatibility between Lombok and MapStruct.
- **Maven Compiler Plugin 3.14.0** – Compiles Java code with annotation processing support.
- **Maven Checkstyle Plugin 3.6.0** – Enforces consistent code style using `checkstyle.xml`.

## 📦 API Documentation

- **SpringDoc OpenAPI (Swagger UI) 2.8.6** – Generates Swagger/OpenAPI docs from Spring REST controllers.

## 🧪 Testing and Containerization

- **Spring Boot Test** – Built-in support for integration and unit testing.
- **Spring Security Test** – Facilitates security-related test scenarios.
- **Testcontainers 1.21.1** – Enables MySQL and JUnit integration tests using containerized services.

---

> ✨ This stack ensures high performance, maintainability, and developer productivity, making Legimus reliable and production-ready.

## 🔧 Setup and Installation
Not ready for setting up my application locally yet? Then explore [Landing](https://legimus-landing.adammudrak.space/) first!<br>There, you will be able to explore Swagger documentation.

1. **Prerequisites:**
    - Software **required**:
        - Git
        - Maven
        - Docker
    ```sh
      #check if everything is installed
      #by checking version of software
      git -v
      mvn -v
      docker -v
    ``` 
   **Open git bash**
   ```sh
      #clone the repository
      git clone https://github.com/AdamMudrak/online_book_shop.git
      #change to online_book_shop root package
      cd online_book_shop/
    ```
   ```sh
      #to change environment variables, you can now use
      nano .env.sample
   ```
    - If you want to use your **own** MySQL, update [application.properties](src/main/resources/application.properties) directly or [.env.sample](.env.sample) with your MySQL credentials.
        - If not, just proceed with the next step as follow-up commands are ready to start MySQL locally in docker container.
    - Having a resend API key **or** adjusting [EmailService](src/main/java/com/example/onlinebookshop/services/email/EmailService.java) to use Google SMTP **is a must**.
        - Having a [Resend account](https://resend.com) **is a must** if using Google SMTP is unwanted;
            - [Get API key](https://apidog.com/blog/resend-api/#1-sign-up-and-create-an-api-key)
        - Having a spare domain for email address to use Resend **is highly recommended**;
            - [Verify your domain](https://apidog.com/blog/resend-api/#2-verify-your-domain)
        - After successful registration, domain verification and getting API token, in [.env.sample](.env.sample) replace values for:
            - RESEND_API_KEY=your_resend_api_key
            - MAIL=your_domained_email
    - Recommended, but app will start without adjustment:
        - JWT_SECRET - secure your JWTs with something meaningful

2. **Run the application:**
    ```sh
      #build application archive
      mvn clean package
    ```
    ```sh
      #build application docker image
      docker build -t online_book_shop .
    ```
   ```sh
      #pull mysql docker image
      docker pull mysql:latest
    ```
    ```sh
      #run mysql docker container, save data in /var/lib/mysql
      docker run --name mysql-container \
        -e MYSQL_ROOT_PASSWORD=root \
        -e MYSQL_DATABASE=legimus_db \
        -e MYSQL_USER=legimus_user \
        -e MYSQL_PASSWORD=legimus_pass \
        -p 3307:3306 -v mysql_data:/var/lib/mysql -d mysql
    ```
   ```sh
      #provide needed RESEND_API_KEY & MAIL properties
      nano .env.sample
    ```
    ```sh
      #run application using .env.sample
      #for tests to run successfully, start Docker Desktop!
      docker run -p 8080:8080 --env-file .env.sample online_book_shop
    ```

3. **Access the API documentation:**
    - Navigate to [Swagger UI](http://localhost:8080/swagger-ui/index.html#/) for API exploration.

## 📜 License
Legimus OnlineBookShopApp is released under [Non-Commercial Use License Agreement](LICENSE.md).

---

🌟 **Enjoy seamless task management with Legimus OnlineBookShopApp!**

Still have some questions? Don't hesitate to [reach out](https://www.linkedin.com/in/adam-mudrak-7813b3279/)!
