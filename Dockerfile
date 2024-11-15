FROM openjdk:22-jdk-alpine
WORKDIR /app
COPY target/task-manager-backend.jar app.jar
ENTRYPOINT ["java", "-jar", "manager.jar"]
