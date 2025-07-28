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
- Open ports: `8080`, `8082`, `4200`, `3306`, `6379`
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
### 3. Access the Application

Frontend (Angular): http://<your-ec2-public-ip>:4200
API Gateway: http://<your-ec2-public-ip>:8082
MySQL: port 3306
Redis: port 6379


## Docker Compose Structure

```bash
version: '3.8'
services:
  mysql:
    image: mysql:8.0
    container_name: mysql
    ports:
      - "3306:3306"
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: car_rental_db
    volumes:
      - mysql_data:/var/lib/mysql
      - ./mysql/init:/docker-entrypoint-initdb.d  # Optional: to import .sql file

  redis:
    image: redis:7-alpine
    ports:
      - "6379:6379"

  backend:
    build: ./car_rental_backend
    ports:
      - "8080:8080"
    environment:
      - SPRING_REDIS_HOST=redis
    depends_on:
      - redis
      - mysql

  frontend:
    build: ./car_rental_angular
    ports:
      - "4200:4200"
    depends_on:
      - gateway

  gateway:
    build: ./car_rental_gateway
    ports:
      - "8082:8082"
    environment:
      - SPRING_REDIS_HOST=redis
    depends_on:
      - backend
      - redis

volumes:
  mysql_data:
```

## EC2 Deployment Instructions

### 1. Use AWS Console or SSH into EC2

```bash
ssh -i "your-key.pem" ec2-user@your-ec2-ip

#Install GIT
sudo yum update -y
sudo yum install git -y

#Create  RSA Key
ssh-keygen
cd .ssh/
cat id_rsa.pub
#Coppy the SSH_RSA and Paste it in GIT hub Secrets
ssh -T git@github.com
```

### 2. Install Docker & Docker Compose

```bash
sudo dnf update -y
sudo dnf update -y
sudo dnf install docker -y
sudo systemctl start docker
sudo systemctl enable docker
sudo usermod -aG docker ec2-user

sudo curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)"
  -o /usr/local/bin/docker-compose

sudo usermod -aG docker ec2-user
#sudo chmod +x /usr/local/bin/docker-compose
```
### 4. Clone and Run

```bash
git clone https://github.com/bejoyjose1993/car-renal-app.git
cd car-renal-app
docker-compose up --build -d
```

### 5. Expose IP and Ports 

✅ Step-by-Step: Setting Inbound Rules on AWS EC2
-  Log in to the AWS Console
-  Navigate to EC2 > Instances
-  Select your running instance
-  Identify the Security Group
-  In the instance details at the bottom, find Security groups
- Edit Inbound Rules

| Type         | Protocol | Port Range | Source               | Description                        |
| ------------ | -------- | ---------- | -------------------- | ---------------------------------- |
| HTTP         | TCP      | 80         | Anywhere (0.0.0.0/0) | For web traffic (if using port 80) |
| HTTPS        | TCP      | 443        | Anywhere             | For HTTPS (optional)               |
| Custom TCP   | TCP      | 8080       | Your IP or 0.0.0.0/0 | Spring Gateway                     |
| Custom TCP   | TCP      | 4200       | Your IP or 0.0.0.0/0 | Angular Dev Server                 |
| MySQL/Aurora | TCP      | 3306       | Your IP              | MySQL DB (limit for security)      |
| Custom TCP   | TCP      | 6379       | Your IP              | Redis (limit access)               |
| SSH          | TCP      | 22         | Your IP              | SSH access                         |


## Testing Endpoints
You can test backend APIs via(If rules are set correctly):

```bash
curl http://<ec2-public-ip>:8082/api/auth/hello
```
Use tools like Postman for more complex testing.

## ✅ To Do
-  Add CI/CD pipeline (GitHub Actions / Jenkins)
-  Enable HTTPS (with Let's Encrypt or AWS ACM)
-  Add monitoring (Prometheus/Grafana)
-  Add unit & integration tests
