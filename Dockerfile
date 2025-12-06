FROM gradle:8.5-jdk21
WORKDIR /app
COPY . .
RUN apt-get update 
