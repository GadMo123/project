FROM openjdk:11

WORKDIR /usr/src/app

COPY ./build/libs/*.jar boot-bootcamp.jar

EXPOSE 9000

ENTRYPOINT ["java","-jar","boot-bootcamp.jar"]

