FROM maven:3.8.5-jdk-11
WORKDIR /app
RUN mkdir -p /app
COPY . /app
RUN mvn clean install
RUN mvn site:site
CMD mvn spring-boot:run
EXPOSE 8080