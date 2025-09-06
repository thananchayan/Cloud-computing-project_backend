# CricTicketHub Backend ⚡

This repository contains the **backend microservices** for the CricTicketHub project.  
It is built using **Spring Boot**, **Spring Cloud**, and **PostgreSQL**, and follows a microservices architecture.  
Services include User, Match, Ticket, and Notification, all connected through an API Gateway and registered with Eureka Service Registry.


## 🏗️ Architecture

- **Service Registry** → Eureka server (service discovery)
- **API Gateway** → Spring Cloud Gateway (routing + security)
- **User Service** → Handles authentication & users (JWT auth)
- **Match Service** → Manage matches and seats
- **Ticket Service** → Ticket booking, integrates with Match Service
- **Notification Service** → Async notifications via RabbitMQ
- **PostgreSQL** → Databases (separate DB for each service)
- **RabbitMQ** → Messaging broker

## 🚀 Features
- JWT-based authentication
- Role-based access (Admin/User)
- Microservice communication (REST + RabbitMQ)
- Database per service
- Dockerized with Compose
- CI/CD with GitHub Actions

## 🛠️ Tech Stack
- Java 21, Spring Boot 
- Spring Security + JWT
- Spring Cloud Netflix (Eureka, Gateway)
- PostgreSQL
- RabbitMQ
- Docker & Docker Compose
- Maven

## 📦 Installation

```bash
# Clone repository
git clone https://github.com/thananchayan/Cloud-computing-project_backend.git
cd Cloud-computing-project_backend
```

Build all microservices:

```bash
mvn clean install -DskipTests
```

Run locally:

```bash
docker compose up --build
```

## 🐳 Docker & Compose

Each service has its own `Dockerfile`. Example:

```dockerfile
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

Main `docker-compose.yml` orchestrates:
- PostgreSQL (with init script)
- RabbitMQ
- Redis 
- prometheus
- grafana
- Service Registry, API Gateway
- All microservices
- Frontend

Run all:

```bash
docker compose up --scale ticket-service=2 -d
```

Stop all:

```bash
docker compose down -v
```

## 🔑 Environment Variables
Example `.env`:

```env
JWT_SECRET=your-secret-key
USERS_DB_PASSWORD=users_pwd
MATCHES_DB_PASSWORD=matches_pwd
TICKETS_DB_PASSWORD=tickets_pwd
NOTIFS_DB_PASSWORD=notifs_pwd
```

## 📂 Project Structure

```
backend/
 ├── user-service/
 ├── match-service/
 ├── ticket-service/
 ├── notification-service/
 ├── api-gateway/
 ├── service-registry/
 ├── docker-compose.yml
 └── README.md
```

## 🔒 Security
- HTTPS with self-signed cert (API Gateway)
- JWT authentication across services
- Database hardening (internal-only access)
- Rate limiting filter at API Gateway

## 📊 Monitoring
- Spring Boot Actuator
- Prometheus + Grafana (optional)

---
