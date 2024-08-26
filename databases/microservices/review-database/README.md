# review-database

## Overview:

Project responsible for creating the database used by the [ms-review-service]() microservice for managing reviews

## Entity Relationship Diagram (DER)
![Alt text](../../../_assets/review-der.png?raw=true "Review DER")

## Build & Run

Create docker image
```bash
docker build -f Dockerfile -t review-database:1.0.0 .
```

Run application as a container
```bash
docker run -d -p 8090:8090  --env PROFILE=docker -i -t review-database:1.0.0 
```

## Commands

Migrates a database schema to the current version

```bash
mvn clean flyway:migrate -Dflyway.configFiles=flyway-{ENVIROMNENT}.conf
```
Prints current status/version of a database schema
```bash
mvn clean flyway:info -Dflyway.configFiles=flyway-{ENVIROMNENT}.conf
```

## Version

### 1.0.0

- Spring-Boot 3.3.3
- MySQL 8.0
- Flyaway
- Java 17