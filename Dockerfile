FROM maven:3.8.8-eclipse-temurin-21-alpine AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim
COPY --from=build /target/contact-management-1.0-SNAPSHOT.jar contact-management.jar
EXPOSE 8086

ENTRYPOINT ["java", "-jar", "contact-management.jar"]

