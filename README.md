# Hospital Management

A hospital patient and doctor management application built with **Spring Boot**, Spring MVC, Thymeleaf, Spring Data JPA, and Java 17.

## Features

- Create, view, edit, and delete patient records
- Create, view, edit, and delete doctor records
- Assign one doctor to one patient
- Validate patient and doctor details
- Use an embedded H2 database locally or configure MySQL

## Technology

- Java 17
- Spring Boot 3
- Spring MVC and Thymeleaf
- Spring Data JPA / Hibernate
- H2 or MySQL
- Maven

## Run locally

Install Java 17 or newer and Maven. From the project folder, run:

```sh
mvn spring-boot:run
```

Open [http://localhost:8080](http://localhost:8080). The default configuration uses a local H2 file database at `./data/hospital`; no database setup is needed.

The H2 console is available at [http://localhost:8080/h2-console](http://localhost:8080/h2-console) while the app is running. Use JDBC URL `jdbc:h2:file:./data/hospital`, username `sa`, and a blank password.

## Use MySQL

Create the database and tables using [database.sql](database.sql), then set the MySQL profile and your local credentials:

```sh
export SPRING_PROFILES_ACTIVE=mysql
export DB_URL='jdbc:mysql://localhost:3306/hospital?useSSL=false&serverTimezone=UTC'
export DB_USERNAME='root'
export DB_PASSWORD='your-local-password'
mvn spring-boot:run
```

The password is supplied through an environment variable and should not be committed to GitHub.

## Build a runnable JAR

```sh
mvn clean package
java -jar target/hospital-management-1.0.0.jar
```

## GitHub repository

Source repository: [LynxNerd/HospitalManagement_SpringBoot](https://github.com/LynxNerd/HospitalManagement_SpringBoot)

Clone it with:

```sh
git clone https://github.com/LynxNerd/HospitalManagement_SpringBoot.git
cd HospitalManagement_SpringBoot
```

To publish project changes, make sure the project files are in your cloned repository folder, then run:

```sh
git add .
git commit -m "Update hospital management project"
git push origin main
```
