# Use a lightweight JDK image
# FROM eclipse-temurin:17-jdk-alpine
FROM openjdk:17


# Set working directory
WORKDIR /app

# Copy the built JAR file from the target directory to the container
# COPY target/*.jar app.jar
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar


# Expose the default Spring Boot port (optional but useful)
EXPOSE 8080

# Run the application
#ENTRYPOINT ["java", "-jar", "/app.jar"]

ENTRYPOINT ["java", "-jar", "/app/app.jar"]


