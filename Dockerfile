FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /TodoService
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /TodoService/target/*.jar TodoService.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "TodoService.jar"]
