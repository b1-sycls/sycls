# Base image
FROM openjdk:17-jdk-slim

# Gradle을 사용해 빌드를 실행하는 명령어
CMD ["./gradlew", "clean", "build"]

# Set the working directory
WORKDIR /apps

# Copy the JAR file
COPY sycls-user-api/build/libs/sycls-user-api-0.0.1-SNAPSHOT.jar /apps/app.jar

# Expose the port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "/apps/app.jar"]