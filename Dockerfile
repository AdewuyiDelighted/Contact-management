FROM maven:3.8.8-eclipse-temurin-21-alpine AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim
COPY --from=build /target/contactManagement=0.0.1-SNAPSHOT.jar contactManagement.jar
EXPOSE 8086
ENTRYPOINT ("Java","-jar","contactManagement.jar")

