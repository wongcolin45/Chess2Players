# Use official JDK image
FROM eclipse-temurin:21-jdk

# Set working directory inside container
WORKDIR /app

# Copy the JAR file built by Maven (adjust the name if needed)
COPY target/*.jar app.jar

# Expose port Spring Boot runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]


#docker run -p 8080:8080 chess-backend