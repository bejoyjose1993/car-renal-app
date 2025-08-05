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
docker-compose --env-file .env.local up --build
```
### 3. Access the Application

Frontend (Angular): http://<your-ec2-public-ip>:4200
API Gateway: http://<your-ec2-public-ip>:8082
MySQL: port 3306
Redis: port 6379


## Docker Compose File

### Docker-compose.yml
```bash
version: '3.8'
services:
  mysql:
    image: mysql:8.0
    container_name: mysql
    environment:
      MYSQL_ROOT_PASSWORD: ${MYSQL_ROOT_PASSWORD}
      MYSQL_DATABASE: ${MYSQL_DATABASE}
    volumes:
      - mysql_data:/var/lib/mysql
      - ./mysql/init:/docker-entrypoint-initdb.d  # Optional: to import .sql file

  redis:
    image: redis:7-alpine

  backend:
    environment:
      - SPRING_REDIS_HOST=redis
    depends_on:
      - redis
      - mysql

  frontend:
    depends_on:
      - gateway

  gateway:
    environment:
      - SPRING_REDIS_HOST=redis
    depends_on:
      - backend
      - redis

volumes:
  mysql_data:
```
### Docker-compose.override.yml
```bash
version: '3.8'

services:
  mysql:
    ports:
      - "${MYSQL_PORT}:${MYSQL_PORT}"
    volumes:
      - mysql_data:/var/lib/mysql
      - ./mysql/init:/docker-entrypoint-initdb.d  # Optional: to import .sql file

  redis:
    ports:
      - "${REDIS_PORT}:${REDIS_PORT}"

  backend:
    build: ./car_rental_backend
    ports:
      - "${BACKEND_PORT}:${BACKEND_PORT}"


  frontend:
    build:
      context: ./car_rental_angular
      args:
        - BASE_API_URL=${BASE_API_URL}

    ports:
      - "${FRONTEND_PORT}:${FRONTEND_PORT}"

  gateway:
    build: ./car_rental_gateway
    ports:
      - "${GATEWAY_PORT}:${GATEWAY_PORT}"
```
### 📂 env.local (on EC2): 
```bash
# .env — Deployment config
# API URL
BASE_API_URL=http://localhost:8082/
# MySQL
MYSQL_ROOT_PASSWORD=root
MYSQL_DATABASE=car_rental_db
MYSQL_PORT=3306
# Redis
REDIS_PORT=6379
# Backend
BACKEND_PORT=8080
# Gateway
GATEWAY_PORT=8082
# Frontend
FRONTEND_PORT=4200
# Docker image tags (optional for versioning)
IMAGE_TAG=latest

```

## EC2 Manual Deployment Instructions 

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

sudo curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose

sudo usermod -aG docker ec2-user
sudo chmod +x /usr/local/bin/docker-compose

Disconnect and Reconnect the instance
```
### 4. Clone and Run

```bash
git clone git@github.com:bejoyjose1993/car-renal-app.git
cd car-renal-app
docker-compose --env-file .env.local up --build
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
| Custom TCP   | TCP      | 8082       | Your IP              | Spring Gateway                     |
| Custom TCP   | TCP      | 4200       | Your IP or 0.0.0.0/0 | Angular Dev Server                 |
| Custom TCP   | TCP      | 6379       | Your IP              | Redis (limit access)               |
| MySQL/Aurora | TCP      | 3306       | Your IP              | MySQL DB (limit for security)      |
| HTTP         | TCP      | 80         | Anywhere (0.0.0.0/0) | For web traffic (if using port 80) |
| HTTPS        | TCP      | 443        | Anywhere             | For HTTPS (optional)               |
| SSH          | TCP      | 22         | Your IP              | SSH access                         |







## EC2 CICD Deployment using GitHub Actions 
A full-stack, containerized **Car Rental Application** deployed to an AWS EC2 instance using **GitHub Actions for automated CI/CD**, **Docker Compose**, and **Docker Hub**

Note:- EC2 instance will only have the folder  /car-renal-app with below files
- docker-compose.yml (Make sure its upto date and latest pulled from repo)
- .mysql
- ReadMe (Not Required)

### 🧱 Tech Stack
| Layer      | Technology               |
|------------|--------------------------|
| CI/CD      | GitHub Actions           |
| Deployment | AWS EC2 + Docker Compose |

## 🚀 Automated CI/CD Workflow
### ✅ Trigger:
> On every push to the `main` branch.

### 🛠️ GitHub Actions Flow:
1. **Build Docker images** for:
   - `car_rental_backend`
   - `car_rental_gateway`
   - `car_rental_angular`
2. **Tag** and **push** to Docker Hub.
3. **SSH into EC2** using `appleboy/ssh-action`.
4. **Pull latest Docker images**.
5. **Restart containers** via Docker Compose.

### 📂 Workflow file: `.github/workflows/deploy.yml`
```bash
name: CI/CD Pipeline

on:
  push:
    branches: [ "main" ]

jobs:
  build-and-deploy:
    runs-on: ubuntu-latest

    steps:
    - name: Checkout code
      uses: actions/checkout@v4

    - name: Set up Docker
      uses: docker/setup-buildx-action@v3
      
    - name: Log in to Docker Hub
      uses: docker/login-action@v3
      with:
        username: ${{ secrets.DOCKER_USERNAME }}
        password: ${{ secrets.DOCKER_PASSWORD }}

        
    - name: Build and push backend image
      run: |
        docker build -t bejoyjose/car_rental_backend ./car_rental_backend
        docker push bejoyjose/car_rental_backend:latest

    - name: Build and push gateway image
      run: |
        docker build -t bejoyjose/car_rental_gateway ./car_rental_gateway
        docker push bejoyjose/car_rental_gateway:latest

    - name: Build and push frontend image
      run: |
        docker build --build-arg BASE_API_URL=${{ secrets.BASE_API_URL }} -t bejoyjose/car_rental_angular ./car_rental_angular
        docker push bejoyjose/car_rental_angular:latest

    - name: Deploy to EC2
      uses: appleboy/ssh-action@master
      with:
        host: ${{ secrets.EC2_HOST }}
        username: ec2-user
        key: ${{ secrets.EC2_SSH_KEY }}
        script: |
          docker pull bejoyjose/car_rental_backend:latest
          docker pull bejoyjose/car_rental_gateway:latest
          docker pull bejoyjose/car_rental_angular:latest
          cd /home/ec2-user/car-renal-app
          docker-compose -f docker-compose.yml -f docker-compose.prod.yml --env-file .env.production pull
          docker-compose -f docker-compose.yml -f docker-compose.prod.yml --env-file .env.production down
          docker-compose -f docker-compose.yml -f docker-compose.prod.yml --env-file .env.production up -d
          docker image prune -a -f
```

### 📂 Docker-compose.prod.yml (on EC2): 
```bash
version: '3.8'

services:
  mysql:
    ports:
      - "127.0.0.1:${MYSQL_PORT}:${MYSQL_PORT}"
    volumes:
      - mysql_data:/var/lib/mysql
      - ./mysql/init:/docker-entrypoint-initdb.d  # Optional: to import .sql file

  redis:
    ports:
      - "127.0.0.1:${REDIS_PORT}:${REDIS_PORT}"

  backend:
    image: bejoyjose/car_rental_backend:${IMAGE_TAG}
    ports:
      - "${BACKEND_PORT}:${BACKEND_PORT}"

  frontend:
    image: bejoyjose/car_rental_angular:${IMAGE_TAG}
    ports:
      - "${FRONTEND_PORT}:${FRONTEND_PORT}"

  gateway:
    image: bejoyjose/car_rental_gateway:${IMAGE_TAG}
    ports:
      - "${GATEWAY_PORT}:${GATEWAY_PORT}"

```
### 📂 env.production (on EC2): 
```bash
# .env — Deployment config
# API URL
#BASE_API_URL=http://16.171.57.106:8082

# MySQL
MYSQL_ROOT_PASSWORD=root
MYSQL_DATABASE=car_rental_db
MYSQL_PORT=3306

# Redis
REDIS_PORT=6379

# Backend
BACKEND_PORT=8080

# Gateway
GATEWAY_PORT=8082

# Frontend
FRONTEND_PORT=4200

# Docker image tags (optional for versioning)
IMAGE_TAG=latest
```


###  GitHub Secrets Required 
| Secret Name       | Description                               |
| ----------------- | ----------------------------------------- |
| `DOCKER_USERNAME` | Your Docker Hub username                  |
| `DOCKER_PASSWORD` | Your Docker Hub password/security token   |
| `EC2_HOST`        | Public IP or domain of your EC2           |
| `EC2_SSH_KEY`     | Your EC2 private key (`.pem`) as a secret |

###  ✅ How to Deploy
ust push code to the main branch — that’s it!
git add .
git commit -m "Your changes"
git push origin main

GitHub Actions will:
-Build → Push → Deploy
-No manual steps required.


Note:- If you start and stop the EC2 instance the public ip will change and hence we might require changing the EC2_HOST and BASE_API_URL to the latest URL and also manually update .env.production file in the EC2 instance. This is because we aint using Elastic ip.

## Testing Endpoints
You can test backend APIs via(If rules are set correctly):

```bash
curl http://<ec2-public-ip>:8082/api/auth/hello
```
Use tools like Postman for more complex testing.

## Allocated and Deallocated Public Elastic IP (EPI) : 13.49.123.169

## ✅ To Do
-  Add CI/CD pipeline (Jenkins T)
-  Enable HTTPS (with Let's Encrypt or AWS ACM)
-  Add monitoring (Prometheus/Grafana)
-  Add unit & integration tests
