FROM maven:3.8.5-jdk-11
VOLUME .m2 /root/.m2
WORKDIR /app
RUN mkdir -p /app
COPY src pom.xml /app
RUN mvn clean install
ENV ACTIVE=default
ENTRYPOINT ["java", "-Dspring.profiles.active=${ACTIVE}", "-jar", "target/rest-api.jar"]
EXPOSE 8080
