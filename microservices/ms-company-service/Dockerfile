FROM openjdk:17-alpine3.14

ARG PROFILE

ADD target/company-service.jar company-service.jar

EXPOSE 8081

ENTRYPOINT ["java","-Dspring.profiles.active=${PROFILE}","-jar","/company-service.jar"]