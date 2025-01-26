FROM eclipse-temurin:21-jre-alpine

# Set the working directory
WORKDIR /app

# Copy the Spring Boot jar from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]