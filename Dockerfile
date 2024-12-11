# Use the official image as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the host to the container
COPY target/*.jar app.jar

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
