# Use an official OpenJDK runtime as the base image
FROM openjdk:17-jdk-slim

# Set a working directory
WORKDIR /app

# Copy the JAR file to the container
COPY target/<your-microservice>.jar /app/microservice.jar

# Expose the port your application runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/microservice.jar"]
