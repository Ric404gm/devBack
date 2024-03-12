#FROM openjdk:23-windowsservercore-ltsc2022
FROM maven:3.8.7-eclipse-temurin-19

RUN mkdir -p /home/app

COPY .  /home/app

EXPOSE 8070

WORKDIR /home/app 

CMD ["mvn","spring-boot:run"]
