FROM openjdk:latest

WORKDIR /usr/src/app

#Using shadow jar gradle plugin to build fat jar with dependencies
COPY build/libs/my-app-1.0.0-all.jar boot-bootcamp.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","boot-bootcamp.jar"]



# FOR /F "tokens=*" %i IN ('docker images -q') DO docker rmi %i
# docker rmi $(docker images -a -q)