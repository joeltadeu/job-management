FROM openjdk:17-alpine3.14

ARG PROFILE

ADD target/job-service.jar job-service.jar

EXPOSE 8082

ENTRYPOINT ["java","-Dspring.profiles.active=${PROFILE}","-jar","/job-service.jar"]