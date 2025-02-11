# Use Eclipse Temurin JDK 21 for Java 21
FROM maven:3.9.9-eclipse-temurin-21 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven configuration file to cache dependencies
COPY pom.xml .

# Download Maven dependencies (this helps to cache dependencies)
RUN mvn dependency:go-offline

# Copy the entire source code to the container
COPY src ./src

# Build the application and skip tests to speed up the build process
RUN mvn clean package -DskipTests

# Stage 2: Create a lightweight image with Java 21 to run the application
FROM eclipse-temurin:21-jre

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the builder stage
# It assumes that your Spring Boot executable jar is the only .jar file in the target directory
COPY --from=build /app/target/*.jar app.jar

# Expose the port that the Spring Boot application listens on (default is 8080)
EXPOSE 8080

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
