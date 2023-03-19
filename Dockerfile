FROM openjdk:19-alpine

RUN mkdir -p home/app

ADD ./build/libs/*.jar boot-bootcamp.jar

ENTRYPOINT ["java","-jar","boot-bootcamp.jar"]
