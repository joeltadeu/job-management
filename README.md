# Job Management

## Overview

This project focuses on job management and reviews, with the goal of exploring key Spring/Java technologies for microservices development.

The project comprises three simple microservices as outlined below:

- **[ms-company-service](microservices/ms-company-service)**: Handles the creation and management of companies.
- **[ms-job-service](microservices/ms-job-service)**: Handles the creation and management of jobs.
- **[ms-review-service](microservices/ms-review-service)**: Handles the creation and management of reviews.

We began by implementing an API Gateway, which serves as a single entry point for multiple APIs, simplifying their management, security, and monitoring.

Each microservice has its own database, using [MySQL](https://www.mysql.com/) as the relational database. The [Flyway](https://github.com/flyway/flyway) library manages the scripts for creating and updating tables. 

We utilize the [Eureka Server project](https://cloud.spring.io/spring-cloud-netflix/reference/html/), which functions as a Service Registry, enabling other applications (services) to register all their instances. This Eureka Server manages all registered instances, including their addresses (host and port), ensuring the instances are always up to date and monitoring their availability.

Internal communication is handled via REST using the [OpenFeign](https://spring.io/projects/spring-cloud-openfeign) library.

To study resilience concepts in practice, we employ patterns such as circuit breaker and fallbacks, implemented through the [Resilience4J](https://resilience4j.readme.io/docs/getting-started-3) library.

For testing, in addition to unit tests, we have two other projects:

- **[karate-automation-tests](tests/automation-tests)**: This project aims to create integrated tests using the [Karate](https://github.com/karatelabs/karate) library.
- **[gatling-performance-tests](tests/performance-tests)**: This project aims to create performance tests using the [Gatling](https://docs.gatling.io/) library.

This project is using [Grafana](https://grafana.com/docs/grafana/latest/) and [Prometheus](https://prometheus.io/) for monitoring microservices and [Zipkin](https://zipkin.io/) for log tracking to ensure comprehensive observability and performance management.

This project utilizes [Grafana Loki](https://grafana.com/docs/loki/latest/) to efficiently aggregate and manage logs from various sources. By centralizing log data, Loki enables seamless monitoring, troubleshooting, and analysis within a unified platform. It integrates smoothly with Grafana, allowing for easy visualization of log metrics alongside other system metrics, facilitating comprehensive insights into system performance and behavior.

## Architecture
![Alt text](_assets/job-management-architecture.png?raw=true "Job Management Architecture")

## Database

![Alt text](_assets/job-management-der.png?raw=true "Entity Relationship Diagram - DER")

The databases are associated with the projects listed below:
- **[company-database](databases/microservices/company-database)**: Company database schema.
- **[job-database](databases/microservices/job-database)**: Job database schema
- **[review-database](databases/microservices/review-database)**: Review database schema.

## Endpoints 

| Service | Method | EndPoint                                   | Description                              |
|---------|:------:|--------------------------------------------|------------------------------------------|
| Company |  GET   | /api/v1/companies/{id}                     | Return detail of specified company       |
| Company |  GET   | /api/v1/companies                          | Return details of all companies          |
| Company |  POST  | /api/v1/companies                          | Create a company                         |
| Company |  PUT   | /api/v1/books/{id}                         | Update a specific company                |
| Company | DELETE | /api/v1/books/{id}                         | Delete a specific company                |
| Job     |  GET   | /api/v1/companies/{companyId}/jobs/{id}    | Return detail of specified job           |
| Job     |  GET   | /api/v1/companies/{companyId}/jobs         | Return details of all jobs by company    |
| Job     |  POST  | /api/v1/companies/{companyId}/jobs         | Create a job                             |
| Job     |  PUT   | /api/v1/companies/{companyId}/jobs/{id}    | Update a specific job                    |
| Job     | DELETE | /api/v1/companies/{companyId}/jobs/{id}    | Delete a specific job                    |
| Review  |  GET   | /api/v1/companies/{companyId}/reviews/{id} | Return detail of specified review        |
| Review  |  GET   | /api/v1/companies/{companyId}/reviews      | Return details of all reviews by company |
| Review  |  POST  | /api/v1/companies/{companyId}/reviews      | Create a review                          |
| Review  |  PUT   | /api/v1/companies/{companyId}/reviews/{id} | Update a specific review                 |
| Review  | DELETE | /api/v1/companies/{companyId}/reviews/{id} | Delete a specific review                 |

### Documentation and examples ###

### Postman collection

> :information_source: Postman collection can be found in the folder [postman](_assets/postman/job-management.postman_collection.json)

## Infrastructure

### API Gateway

Spring API Gateway is a powerful component within the Spring Cloud ecosystem designed to handle all incoming requests to a microservices architecture. Acting as a reverse proxy, it routes requests to the appropriate backend services, providing essential functionalities such as load balancing, routing, rate limiting, and security.

URI for gateway : *http://localhost:9094*

### Eureka Server

Spring Eureka Server is a key component of the Spring Cloud Netflix stack, providing service discovery capabilities for microservices architectures. It acts as a registry where microservices can register themselves and discover other registered services, enabling dynamic scaling and robust failover mechanisms.

URI for gateway : *http://localhost:8761*

![Alt text](_assets/eureka_screenshot.png?raw=true "Eureka Server")

## Observability

### Zipkin

Zipkin is an open-source distributed tracing system that helps monitor and troubleshoot the latency and performance issues within complex, microservices-based architectures. It tracks the flow of requests through different services by collecting and visualizing trace data, allowing developers to pinpoint bottlenecks, understand service dependencies, and diagnose issues effectively.
Run zipkin as a docker container with the command:

```bash
docker run -d -p 9411:9411 openzipkin/zipkin**
```

You can open Zipkin : http://localhost:9411

![Alt text](_assets/zipkin_screenshot.png?raw=true "Zipkin dependencies")

### Grafana

Grafana is a versatile open-source platform used for monitoring and observability, renowned for its powerful data visualization capabilities. It allows users to create and share interactive, real-time dashboards that integrate with a variety of data sources, such as Prometheus, Elasticsearch, and InfluxDB

You can open Grafana : http://localhost:3000/

username/password: admin

![Alt text](_assets/grafana_screenshot.png?raw=true "Grafana dashboard")

### Grafana Loki

Loki is a horizontally scalable, highly available, multi-tenant log aggregation system inspired by Prometheus. It is designed to be very cost effective and easy to operate. It does not index the contents of the logs, but rather a set of labels for each log stream.

### Prometheus

Prometheus is an open-source monitoring and alerting toolkit designed for reliability and scalability in cloud-native environments. It specializes in collecting and storing time-series data, enabling detailed and high-resolution monitoring of various metrics from different sources.

You can open Prometheus : http://localhost:9090/

![Alt text](_assets/prometheus_screenshot.png?raw=true "Prometheus Graph")

## Tests

### Unit tests

Execute the unit tests using the command bellow:
```bash
mvn test 
```

### Integration tests

#### Karate tests
Karate is a testing framework designed for API testing, offering a simplified and expressive syntax to create and execute tests for web services. It integrates seamlessly with HTTP, allowing for straightforward validation of RESTful APIs by enabling testers to define requests and assertions in a readable, concise manner.

Execute the karate tests using the command bellow:
```bash
mvn test -Dkarate.env=local -Dtest=br.com.customer.karate.KarateTestRunner
```

Results can be found in the [tests/automation-tests/target/karate-reports]() folder

![Alt text](_assets/karate_screenshot.png?raw=true "Karate Test Result")

### Performance tests

#### Gatling

Gatling is a powerful open-source load testing tool designed to analyze and measure the performance of web applications. It allows developers and testers to simulate a large number of users interacting with a system, providing detailed reports and metrics on response times, throughput, and error rates.

Execute the performance tests using the command bellow:
```bash
mvn clean test -DconcurrentUsers={numberOfConcurrentUsers} -DlengthOfTest={periodInMinutes} -DsimulationClass=com.jmanagement.performance.MeshJobManagementHappyPathFlowSimulation
```
![Alt text](_assets/gatling_screenshot.png?raw=true "Gatling performance tests result")


## Build & Run

### Build

In the root folder run the maven command to build all the microservices:

```
mvn clean package
```

### Build as a container

Access each microservice folder and execute the command to build the docker image:

```
docker build -f Dockerfile -t [SERVICE_NAME]:1.0.0 .
```

### Run as a container

After the docker image was created, execute the command to run the container:

```
docker run -d -p [PORT]:[PORT]  --env PROFILE=local -i -t [SERVICE_NAME]:1.0.0
```

### Run using docker compose

There is a project to run all microservices and the third party services using docker compose

First, in the root folder execute the command bellow to build the microservices and generate the jar files

```
mvn clean install
```

#### Build the images and run all the containers

```
docker-compose up
```

#### Stop the containers

```
docker-compose down --rmi all
```

## Technologies Used
* Spring Boot 3.3.3
* Spring API Gateway
* OpenFeign
* Eureka Server
* Resilience4j
* Java 17
* MySQL 8
* Zipkin
* Prometheus
* Grafana
* Loki
* Karate tests
* Gatling
