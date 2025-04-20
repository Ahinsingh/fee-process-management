# 🎓 Microservices Application - Student Management System

This project is a microservices-based system for managing students, courses, and fee processing. It includes the following services:

---

## 📦 Services Overview

| Service Name             | Port  | Description                                       |
|--------------------------|-------|---------------------------------------------------|
| Service Registry         | 8761  | Eureka Server for service discovery              |
| API Gateway              | 8080  | Gateway for routing requests to microservices     |
| Student Service          | 8082  | Manages student data                             |
| Catalog Service          | 8083  | Manages course catalog and course information    |
| Fee Process Service      | 8081  | Handles fee processing and integrates with student and catalog |

---

## 🚀 How to Run the Application

### 🧱 Prerequisites

- Java 17+
- Maven
- Spring Boot

### 🔄 Start Order

1. **Service Registry**

   cd service-registry
   mvn spring-boot:run


2. **API Gateway**

   cd api-gateway
   mvn spring-boot:run


3. **Student Service**
   cd student-service
   mvn spring-boot:run


4. **Catalog Service**

   cd catalog-service
   mvn spring-boot:run


5. **Fee Process Service**

   cd fee-process-service
   mvn spring-boot:run


---

## 📘 API Documentation

Each service exposes Swagger UI for API testing and documentation.

| Service         | Swagger URL                                       |
|----------------|----------------------------------------------------|
| API Gateway     | `http://localhost:8080/swagger-ui.html`          |
| Student Service | `http://localhost:8082/swagger-ui.html`          |
| Catalog Service | `http://localhost:8083/swagger-ui.html`          |
| Fee Service     | `http://localhost:8081/swagger-ui.html`          |

> 🔁 All microservices are aggregated and accessible via the API Gateway.


---

## 🧩 Service Communication

- **Student Service** → Calls **Catalog Service** to fetch course info via Feign Client
- **Fee Service** → Fetches student & course details from respective services
- **API Gateway** → Routes and secures all external requests
- **Eureka** → Enables dynamic service registration/discovery

---

## 🛡️ Tech Stack

- Java 17
- Spring Boot 3.x
- Spring Cloud (Eureka, Gateway, Feign)
- Maven
- Swagger (SpringDoc OpenAPI)
- Resilience4j (Circuit Breaker)

