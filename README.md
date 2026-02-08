# 🚀 Course Platform — Backend Service (Spring Boot)
# 📚 Course Platform (Backend)

## 🔹 Overview

**Course Platform** is a Spring Boot-based backend application that provides REST APIs for managing users, courses, topics, enrollments, and progress tracking.  
It uses **JWT-based authentication**, **PostgreSQL as the primary database**, and is deployed on **Railway**.

The project is built following standard layered architecture and is designed to be secure, scalable, and production-ready.
It can be accessed here-> https://course-platform-production.up.railway.app/swagger-ui/index.html

## 🏗️ High-Level Architecture

```
                    +----------------------+
                    |      Client         |
                    | (Postman / Browser) |
                    +----------+---------+
                               |
                               | HTTP (REST)
                               v
                    +----------------------+
                    |   Spring Boot App   |
                    +----------+---------+
                               |
        +----------------------+----------------------+
        |                      |                      |
        v                      v                      v
 +-------------+       +---------------+      +----------------+
 | Controllers | --->  |   Services    | ---> | Repositories   |
 +-------------+       +---------------+      +----------------+
                               |
                               v
                     +-------------------+
                     |   PostgreSQL DB  |
                     +-------------------+
```

## 🔐 Security Flow (JWT Authentication)

```
Client
|
| POST /auth/login  (email + password)
v
+-------------------+
|  Auth Controller  |
+-------------------+
|
v
+-------------------+
|  JWT Generated    |
+-------------------+
|
v
Client receives JWT token
|
|  Authorization: Bearer <token>
v
+-------------------------+
| JwtAuthenticationFilter |
+-------------------------+
|
v
Spring SecurityContext is set
|
v
Request reaches Controller

```

## 📦 Tech Stack

| Component | Technology |
|----------|------------|
| Backend | Spring Boot 4 |
| Security | Spring Security + JWT |
| Database | PostgreSQL |
| ORM | Spring Data JPA (Hibernate) |
| API Docs | SpringDoc OpenAPI (Swagger UI) |
| Build Tool | Maven |
| Deployment | Railway |

---

## 📁 Project Structure

```

src/main/java/com/vimarsh/Course_Platform/
│
├── CoursePlatformApplication.java
│
├── Controller/
│   ├── EnrollmentController.java
│   ├── CourseController.java
│   ├── UserController.java
│   ├── ProgressController.java
|
├── Service/
│   ├── AuthService.java
│   ├── DataImportService.java
│   ├── EnrollmentService.java
│   ├── ProgressService.java
│   ├── UserService.java
|
├── Repository/
│   ├── UserRepository.java
│   ├── CourseRepository.java
│   ├── EnrollmentRepository.java
│   ├── SubTopicRepository.java
│   ├── SubTopicProgressRepository.java
│
├── Model/
│   ├── User.java
│   ├── Course.java
│   ├── Topic.java
│   ├── SubTopic.java
│   ├── SubTopicProgress.java
│   ├── Enrollment.java
|
├── DataTransferObjects/
│   ├── ApiError.java
│   ├── CompletedItemDto.java
│   ├── CourseSummaryDTO.java
│   ├── LoginRequestDTO.java
│   ├── LoginResponseDTO.java
│   ├── ProgressResponse.java
│   ├── UserRequestDTO.java
│   ├── UserResponseDTO.java
│
├── Security/
│   ├── SecurityConfig.java
|
├── Utility/
│   ├── JwtAuthenticationFilter.java
│   ├── JwtUtil.java
│
└── exception/
    ├── GlobalExceptionHandler.java
    ├── AlreadyEnrolledException.java
    ├── ForbiddenException.java
    ├── ResourceNotFoundException.java
    ├── AlreadyEnrolledException.java
    ├── UserAlreadyExistsException.java
    ├── UserBadRequestDTOError.java
    ├── UserDatabaseSaveException.java

```

## 🗄️ Database Schema (Simplified)

```

+-----------+       +-------------+       +---------------+
|   users   |<----- | enrollment  | ----> |    course     |
+-----------+       +-------------+       +---------------+
| id (PK)   |       | id (PK)     |       | id (PK)       |
| email     |       | user_id (FK)|       | title         |
| password  |       | course_id(FK)|      | description   |
+-----------+       +-------------+       +---------------+
                          ^                       ^
                          |                       |
                          |                       |
                          |                +------------+
          +-------------------------+      |   topic    |
          |   sub_topic_progress    |      +------------+
          +-------------------------+      | id (PK)    |
           | id (PK)                |      | title      |
           | user_id (FK)           |      | course_id  |
           | subtopic_id (FK)       |      +------------+
           | completed (boolean)    |            ^
           | completed_at           |            |
          +-------------------------+            |                                    
                      |                          |
                      |                          |
                      |                  +----------------+
                      |                  |   sub_topic    |  
                      |                  +----------------+  
                      |                  | id (PK)        |
                      └── ── ── ── ── ──>| title          |
                                         | content (TEXT) |        
                                         | topic_id (FK)  |        
                                         +----------------+        
                                 


```

## 🌐 Swagger / API Documentation

Once the application is running, access Swagger UI at:

```

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

```
or (on Railway)
```

https://<your-railway-app>.up.railway.app/swagger-ui.html

```

## 🚀 Deployment (Railway)

### Environment Variables (Railway)

Set these in Railway → Variables:

```

PORT=8080
SPRING_DATASOURCE_URL=jdbc:postgresql://<railway-db-host>:5432/railway
SPRING_DATASOURCE_USERNAME=railway
SPRING_DATASOURCE_PASSWORD=<your-db-password>
JWT_SECRET=yourSuperSecretKey

````

---

## ▶️ How to Run Locally

### 1️⃣ Clone repo
```bash
git clone https://github.com/your-username/course-platform.git
cd course-platform
````

### 2️⃣ Run with Maven

```bash
./mvnw spring-boot:run
```

### 3️⃣ Access APIs

* Backend: `http://localhost:8080`
* Swagger: `http://localhost:8080/swagger-ui.html`

---

## ✅ Features

- JWT Authentication  
- Role-based Security (extensible)  
- RESTful APIs  
- PostgreSQL Integration  
- Swagger Documentation  
- Railway Deployment Ready  
- Global Exception Handling  

---

## 👨‍💻 Author

**Vimarsh Sharma**  
Backend Developer  
Spring Boot | Java | PostgreSQL | Security | Railway  
