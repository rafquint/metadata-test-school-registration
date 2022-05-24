FROM openjdk:11
RUN mkdir -p /app
COPY target/rest-api.jar /app
WORKDIR /app
ENTRYPOINT ["java", "-jar", "rest-api.jar"]
EXPOSE 8080
