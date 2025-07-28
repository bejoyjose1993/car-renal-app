# 🚀 Full Stack Dockerized Application "# car-renal-app" 

This repository contains a **Dockerized Full-Stack Application** with the following tech stack:

- **Frontend**: Angular  
- **Backend**: Spring Boot  
- **API Gateway**: Spring Cloud Gateway  
- **Database**: MySQL  
- **Caching**: Redis  
- **Deployment**: AWS EC2 instance

---

## 🧱 Architecture Overview

[ Angular App ] --> [ Spring Cloud Gateway ] --> [ Spring Boot Services ]
| [ MySQL | Redis ]


Each component is containerized using Docker and orchestrated via Docker Compose for local development and deployment on an AWS EC2 instance.

---

## 📦 Tech Stack

| Layer        | Technology          |
|--------------|---------------------|
| Frontend     | Angular             |
| Backend      | Spring Boot         |
| API Gateway  | Spring Cloud Gateway|
| Database     | MySQL               |
| Caching      | Redis               |
| Deployment   | Docker, Docker Compose, AWS EC2 |

---

## 🛠️ Prerequisites
- Docker & Docker Compose installed
- AWS EC2 instance (Amazon Linux 2 / Ubuntu preferred)
- Open ports: `80`, `443`, `8080`, `4200`, `3306`, `6379`
- SSH access to your EC2 instance
---

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/fullstack-docker-app.git
cd fullstack-docker-app
```
### 2. Build and Run with Docker Compose
```bash
docker-compose up --build
```
### 2. Access the Application

Frontend (Angular): http://<your-ec2-public-ip>:4200
API Gateway: http://<your-ec2-public-ip>:8082
MySQL: port 3306
Redis: port 6379
