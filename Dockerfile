FROM maven:3.8.5-jdk-11
WORKDIR /app
RUN mkdir -p /app
COPY src pom.xml /app
RUN mvn clean install
ENTRYPOINT ["java", "-Dspring.profiles.active=default", "-jar", "target/rest-api.jar"]
EXPOSE 8080
