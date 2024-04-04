FROM openjdk:latest

WORKDIR /usr/src/app

COPY build/libs/my-app-1.0.0-all.jar boot-bootcamp.jar
COPY build/resources/* resources

EXPOSE 8080

ENTRYPOINT ["java","-jar","boot-bootcamp.jar"]
