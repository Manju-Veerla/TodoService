FROM maven:3.8.5-openjdk-17
WORKDIR /Todo-app
COPY . .
RUN mvn clean install
COPY target/todorestapi-0.0.1-SNAPSHOT.jar .

EXPOSE 8080
ENV JAVA_OPTS=""
CMD ["java", "-jar", "todorestapi-0.0.1-SNAPSHOT"]