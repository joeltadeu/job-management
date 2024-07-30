FROM openjdk:17-alpine3.14

ARG PROFILE

ADD target/review-service.jar review-service.jar

EXPOSE 8083

ENTRYPOINT ["java","-Dspring.profiles.active=${PROFILE}","-jar","/review-service.jar"]