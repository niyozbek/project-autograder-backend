# Overview
An autograding web application is a software tool designed to automatically evaluate and grade assignments.
Frontend web application is available at https://github.com/niyozbek/project-autograder-app.

# Requirements
- Docker

## Dev environment set-up (Optional):
- Install java open-jdk:17
- Install gradle:
  `sdk install gradle`
  `gradle --version`
  `gradle build`
  `gradle bootRun`

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

- View changes to code - restart app:
  `docker compose -f compose.local.yaml restart app`

### Access container terminal:
    docker compose -f compose.local.yaml exec {SERVICE} bash
Example:

    docker compose -f compose.local.yaml exec app bash

# Get started
Three roles are created with migration by default:
-admin role -> {username: admin, password: admin}
-lecturer role -> {username: alex, password: alex}
-student role -> {username: patrick, password: patrick}

# REST API DOCUMENTATION: 
http://localhost:8080/swagger-ui/index.html#/
