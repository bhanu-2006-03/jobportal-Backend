# ==========================================================
# JobNest Full-Stack Deployment Dockerfile (Render / Railway)
# ==========================================================

# Stage 1: Build the Spring Boot application (with embedded React frontend)
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml and source files
COPY pom.xml .
COPY src ./src

# Package production executable JAR (tests skipped for fast cloud deployment)
RUN mvn clean package -DskipTests

# Stage 2: Minimal JRE runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy built JAR from build stage
COPY --from=build /app/target/jobnest-backend-1.0.0.jar app.jar

# Dynamic port binding for Render / Railway / Heroku
EXPOSE 8080
ENV PORT=8080
ENV SPRING_PROFILES_ACTIVE=h2

ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT} -jar app.jar"]
