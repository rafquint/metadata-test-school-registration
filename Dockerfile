FROM maven:3.8.5-jdk-11
VOLUME .m2 /root/.m2
WORKDIR /app
RUN mkdir -p /app
COPY src pom.xml /app
RUN mvn clean install
ENV ACTIVE=default
ENTRYPOINT ["mvn", "spring-boot:run", "-Dspring.profiles.active=${ACTIVE}"]
EXPOSE 8080
