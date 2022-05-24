# metadata-test-school-registration
School registration system

# Project setup

1. Clone

	git clone 

3. Build

    mvn clean install

4. Generate jacoco reports

    mvn site:site

5. Run

    mvn spring-boot:run

    Browse:
        http://localhost:8080/
        or
        http://localhost:8080/swagger-ui/

6. Docker

    docker build -t school:1.0 .

7. Docker Compose

    docker-compose up
