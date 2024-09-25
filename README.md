# NewsTok Admin Backend

## Overview

The NewsTok Admin Backend is a Spring Boot application designed to manage news, reels, and user interactions for the NewsTok platform. It provides endpoints for admin login, user management, news processing, and reel recommendations.

## Table of Contents
- [Features](#Features)
- [Setup and Installation](#setup-and-installation)
- [Configuration](#configuration)
  - [Property Explanations](#property-explanations)
- [Endpoints](#endpoints)
## Features

![WhatsApp Image 2024-09-25 at 22 07 35_ff7079d4](https://github.com/user-attachments/assets/13ded7d4-c2a5-41f3-82be-2b016403aa77)

- **Admin Authentication:** Secure login with JWT tokens.
- **Dashboard Data:** Retrieve and manage dashboard information.
- **News Management:** Create and retrieve news articles.
- **Reels Management:** Generate and manage news reels.
- **Recommendations:** Get recommendations based on user interactions.

## Setup and Installation

1. **Install Java 17**

   Ensure you have Java 17 installed on your machine. You can verify this by running the following command:

   ```bash
   java -version
   ```

   If you do not have Java 17, download and install it from the [official website](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).

2. **Clone the Repository**

   ```bash
   git clone https://github.com/PaleBlueDot-repo/newstok-admin-java-backend.git
   ```

3. **Navigate to the Project Directory**

   ```bash
   cd newstok-admin-java-backend/Admin 
   ```

4. **Setup Database Using XAMPP**

   - Open XAMPP and start the **MySQL** service.
   - Open **phpMyAdmin** (usually available at `http://localhost/phpmyadmin`).
   - Create a new database named `admindb`.

   Ensure that the MySQL port is set to `3306` (which is the default port in the `application.properties`).

5. **Build the Project**

   ```bash
   ./mvnw clean install
   ```

6. **Run the Application**

   ```bash
   ./mvnw spring-boot:run
   ```

## Configuration

### `example.application.properties` File

The application configuration is managed through the `application.properties` file. An example configuration file named `example.application.properties` is provided in the repository. You need to create a `application.properties` file in the `src/main/resources` directory by copying the example file and filling in the necessary fields.

### Steps to Create and Configure `application.properties`

1. **Copy the Example File**

   In the root of the project, there is an `example.application.properties` file. Copy this file to create your `application.properties`:

2. **Fill in the Required Fields**

   Open the newly created `application.properties` file and fill in the required fields with your specific configuration details. Below is a template and explanations for each property:

   ```properties
   # Spring Boot Application Name
   spring.application.name=admin

   # Database Configuration
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   spring.datasource.url=jdbc:mysql://localhost:3306/admindb
   spring.datasource.username=root
   spring.datasource.password=YOUR_DB_PASSWORD_HERE
   # Ensure the port matches your MySQL setup; default is 3306

   # JPA/Hibernate Configuration
   spring.jpa.show-sql=true
   spring.jpa.hibernate.ddl-auto=update

   # JWT Configuration
   security.jwt.secret-key=YOUR_SECRET_KEY_HERE
   security.jwt.expiration-time-ms=86400000
   security.jwt.issuer=YOUR_ISSUER_HERE

   # Admin Authentication
   AdminToUserAuthentication.email=YOUR_ADMIN_EMAIL_HERE
   AdminToUserAuthentication.password=YOUR_ADMIN_PASSWORD_HERE

   # Codec Configuration
   spring.codec.max-in-memory-size=1000KB

   # Reels Recommendation Settings
   MaxNumberOfReels.n=30

   # External API URLs
   reels.recommendation.api.url=http://localhost:5000/ml/recommendReels
   dashboard.api.url=http://localhost:8080/user/getDashboard
   newsreels.api.process.url=http://127.0.0.1:5000/ml/process-data
   newsreels.api.image.url=http://127.0.0.1:5000/ml/generate-image
   news.api.bangla.url=/scraping/scrape_bangla_news
   news.api.english.url=/scraping/scrape_english_news
   user.login.api.url=http://localhost:8080/user/login

   # API Key for External Services
   api.key=YOUR_API_KEY_HERE

   # Eureka Client Configuration
   eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka/
   ```

### Property Explanations

- **`spring.application.name`**: The name of the Spring Boot application.
- **`spring.datasource.driver-class-name`**: The driver class name for the database.
- **`spring.datasource.url`**: The URL for connecting to the MySQL database. Ensure the port matches your MySQL setup (default is 3306).
- **`spring.datasource.username`**: The username for the database.
- **`spring.datasource.password`**: The password for the database. **(Fill this in with your database password)**
- **`spring.jpa.show-sql`**: If true, SQL statements will be logged to the console.
- **`spring.jpa.hibernate.ddl-auto`**: Specifies the database schema management. `update` will update the schema automatically.
- **`security.jwt.secret-key`**: The secret key used to sign JWT tokens. **(Set your secret key)**
- **`security.jwt.expiration-time-ms`**: The expiration time for JWT tokens in milliseconds (e.g., 86400000 ms = 1 day).
- **`security.jwt.issuer`**: The issuer of the JWT tokens. **(Set your issuer name)**
- **`AdminToUserAuthentication.email`**: The default email for admin authentication. **(Set your admin email)**
- **`AdminToUserAuthentication.password`**: The default password for admin authentication. **(Set your admin password)**
- **`spring.codec.max-in-memory-size`**: Maximum size of in-memory data (e.g., for file uploads).
- **`MaxNumberOfReels.n`**: The maximum number of reels to recommend.
- **`reels.recommendation.api.url`**: URL for the external reels recommendation API.
- **`dashboard.api.url`**: URL for retrieving dashboard data.
- **`newsreels.api.process.url`**: URL for processing news data.
- **`newsreels.api.image.url`**: URL for generating reel images.
- **`news.api.bangla.url`**: Endpoint for scraping Bangla news.
- **`news.api.english.url`**: Endpoint for scraping English news.
- **`user.login.api.url`**: URL for user login.
- **`api.key`**: API key for authentication with external services. **(Set your API key)**
- **`eureka.client.serviceUrl.defaultZone`**: URL for the Eureka service registry.

## Endpoints

### Authentication

- **POST /admin/login**: Authenticates an admin and returns a JWT token.
- **POST /admin/signup**: Registers a new admin user.

### Dashboard

- **GET /admin/getDashboard**: Retrieves full dashboard data.

### News

- **POST /admin/getNews**: Creates news based on the provided name and category.
- **GET /admin/getAllNews**: Retrieves all news articles.

### Reels

- **POST /admin/getAllReels**: Retrieves and creates reels based on provided news IDs.

### Recommendations

- **POST /admin/getReelsRecommendation**: Retrieves recommended reels based on user interaction data.





Changing Maximum Packet Size Limit in SQL


## MySQL

### On Linux

1. **Locate Configuration File**
   - Find the MySQL configuration file, typically `my.cnf` or `my.ini`.
   - Common paths: `/etc/mysql/my.cnf` or `/etc/my.cnf`.

2. **Edit Configuration File**
   - Open the file with root privileges:
     ```bash
     sudo nano /etc/mysql/my.cnf
     ```
   - Add or modify the `max_allowed_packet` setting under the `[mysqld]` section:
     ```ini
     [mysqld]
     max_allowed_packet=64M
     ```
   - Save and close the file.

3. **Restart MySQL Service**
   - Restart the MySQL service to apply changes:
     ```bash
     sudo systemctl restart mysql
     ```
   - Alternatively:
     ```bash
     sudo service mysql restart
     ```

### On Windows

1. **Locate Configuration File**
   - Find `my.ini` in the MySQL installation directory, such as `C:\xampp\mysql\bin\my.ini
     `. or `C:\ProgramData\MySQL\MySQL Server X.Y\my.ini`.

2. **Edit Configuration File**
   - Open `my.ini` with a text editor (Notepad) as an administrator.
   - Add or modify the `max_allowed_packet` setting under the `[mysqld]` section:
     ```ini
     [mysqld]
     max_allowed_packet=64M
     ```
   - Save and close the file.

3. **Restart MySQL Service**
   - Open Services from the Start menu.
   - Locate and restart the MySQL service.


