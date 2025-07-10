FROM gradle:8.5-jdk17
WORKDIR /app
COPY . .
RUN apt-get update && apt-get install -y curl
