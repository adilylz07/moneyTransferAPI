# Use the official Maven image to create a build artifact
FROM maven:3.8.3-openjdk-11 AS build
COPY pom.xml /app/
COPY src /app/src/
WORKDIR /app
RUN mvn clean package -DskipTests

# Use the official OpenJDK image as the base image for running the application
FROM openjdk:11-jre-slim
COPY --from=build /app/target/transferAPI-0.0.1-SNAPSHOT.jar /app/transferAPI.jar
ENTRYPOINT ["java", "-jar", "/app/transferAPI.jar"]
