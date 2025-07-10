# Overview
An autograder web application is a software tool designed to automatically evaluate and grade assignments.
Frontend web application is available at https://github.com/niyozbek/project-autograder-app.

# Requirements
- Docker

# Version
- Spring Boot 3.5.x
- Postgres 17.x

## Dev environment set-up (Optional):
- Install java open-jdk:17
- Install gradle:
  `sdk install gradle`
  `gradle --version`
  `gradle build`
  `gradle bootRun`
  `gradle wrapper`
- Documentation: https://docs.spring.io/spring-boot/docs/2.6.6/reference/htmlsingle

## Use openssl to generate keys or secrets:
    openssl rand -base64 18
    openssl rand -base64 36
    openssl rand -base64 64

## Run the project (Docker)

### Run `compose.local.yml`:
- Down:
  `docker compose -f compose.local.yaml down`

- Down with volumes:
  `docker compose -f compose.local.yaml down -v`

- Up with build:
  `docker compose -f compose.local.yaml up --build -d`

- Apply changes in the app - build and restart the app:
  `docker compose -f compose.local.yaml build app && docker compose -f compose.local.yaml restart app`

## Run the project in debug mode via IntelliJ:
- Up redis and pgsql for local profile:
  `docker compose -f compose.local.yaml up redis pgsql pgsql-test --build -d`
- Configure application-local.yml.
- Set the profile to local in IntelliJ.
  `export SPRING_PROFILES_ACTIVE=local`
  `gradle classes`
  `gradle test --debug`
  `gradle bootRun`

### Access container terminal:
  `docker compose -f compose.local.yaml exec {SERVICE} bash`
Example:
  `docker compose -f compose.local.yaml exec app bash`

# Get started
Three roles are created with migration by default:
-admin role: {username: admin, password: admin}
-lecturer role: {username: alex, password: alex}
-student role: {username: patrick, password: patrick}

# REST API DOCUMENTATION: 
http://localhost:8080/swagger-ui/index.html#/
