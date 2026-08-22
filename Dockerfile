FROM openjdk:17-jdk-alpine
MAINTAINER derek
COPY target/server.jar server.jar
ENTRYPOINT ["java", "-jar","server.jar"]