# Overview
An autograder web application designed to automatically evaluate and grade assignments using a secure, containerized execution environment.
Frontend web application is available at https://github.com/niyozbek/project-autograder-app.

# Requirements
- Docker & Docker Compose
- Java 21 (Open JDK)
- Piston (Code Execution Engine - handled via Docker)

# Tech Stack
- **Framework**: Spring Boot 3.5.x
- **Database**: PostgreSQL 17.x
- **Security**: Spring Security + JWT + Role-Based Access Control (RBAC)
- **Real-time**: WebSocket (StompJS) with Query Param Auth
- **Execution**: Piston (Self-hosted via Docker)

## Dev environment set-up (Optional):
- Install java open-jdk:21
- Install gradle:
  `sdk install gradle`
  `gradle --version`
  `gradle build`
  `gradle bootRun`
  `gradle wrapper`
- Documentation: https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle

## Use openssl to generate keys or secrets:
    openssl rand -base64 18
    openssl rand -base64 36
    openssl rand -base64 64

## Run the project (Docker)

### Run `compose.local.yml`:
- **Start All Services (App, DB, Redis, Piston):**
  `docker compose -f compose.local.yaml up --build -d`

- **Stop:**
  `docker compose -f compose.local.yaml down`

- **Stop & Clean Volumes:**
  `docker compose -f compose.local.yaml down -v`

- **Rebuild App Only:**
  `docker compose -f compose.local.yaml build app && docker compose -f compose.local.yaml restart app`

## Run the project in debug mode via IntelliJ:
- Up dependencies (Redis, Postgres, Piston):
  `docker compose -f compose.local.yaml up redis pgsql piston --build -d`
- Configure `application-local.yml`.
- Set the profile to `local` in IntelliJ.
  `export SPRING_PROFILES_ACTIVE=local`
  `gradle classes`
  `gradle test --debug`
  `gradle bootRun`

### Access container terminal:
  `docker compose -f compose.local.yaml exec {SERVICE} bash`
Example:
  `docker compose -f compose.local.yaml exec app bash`

# Roles & Security
Three default roles are created via Flyway migrations:
- **Admin**: `{username: admin, password: admin}` - Full system access.
- **Lecturer**: `{username: alex, password: alex}` - Manage problems & submissions.
- **Student**: `{username: patrick, password: patrick}` - Submit solutions.

# REST API DOCUMENTATION: 
http://localhost:8080/swagger-ui/index.html#/


# Enable user
curl -X PATCH http://localhost:8095/api/debug/users/2/enable

# Disable user  
curl -X PATCH http://localhost:8095/api/debug/users/2/disable

# Check status
curl http://localhost:8095/api/debug/users/2