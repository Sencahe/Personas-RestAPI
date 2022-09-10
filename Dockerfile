FROM openjdk:16
ADD target/personas-rest-api-1.0.jar personas-rest-api-1.0.jar
ENTRYPOINT ["java", "-jar","personas-rest-api-1.0.jar"]
EXPOSE 8080