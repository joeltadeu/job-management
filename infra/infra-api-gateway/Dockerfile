FROM openjdk:17-alpine3.14

ARG PROFILE

ADD target/api-gateway-service.jar api-gateway-service.jar

EXPOSE 9094

ENTRYPOINT ["java","-Dspring.profiles.active=${PROFILE}","-jar","/api-gateway-service.jar"]