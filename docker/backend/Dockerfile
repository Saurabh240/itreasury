# Build stage
FROM gradle:8.13-jdk17-ubi AS build
# Set the working directory inside the Docker container
WORKDIR /build

# Copy Gradle build files and source code
RUN ls -la /build
COPY ../../itreasury/ /build/
RUN ls -la /build

# Change the permissions of the Gradle wrapper script
RUN chmod +x gradlew

# Build the application JAR file
RUN ./gradlew clean build -x test

# Use an OpenJDK runtime image for running the app
FROM openjdk:17-jdk-slim

ARG PROFILE=dev
ARG APP_NAME=treasuryAutomation
ARG APP_VERSION=0.0.1-SNAPSHOT


# Set the working directory for the runtime container
WORKDIR /app

# Copy the built JAR file from the build stage to the runtime image
COPY --from=build /build/build/libs/*.jar /app/

# Expose the application's port (e.g., 8080 by default for Spring Boot)
EXPOSE 8090

ENV APP_NAME=${APP_NAME}
ENV JAR_VERSION=${APP_VERSION}
ENV ACTIVE_PROFILE=${PROFILE}
ENV DB_URL=jdbc:postgresql://postgres_sql_itreasury:5432/itreasury_db

# Run the application
CMD java -jar -Dspring.profiles.active=${ACTIVE_PROFILE} -Dspring.datasource.url=${DB_URL} ${APP_NAME}-${JAR_VERSION}.jar