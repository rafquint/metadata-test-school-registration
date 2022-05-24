# metadata-test-school-registration
School registration system

# Project setup

1. Clone

	git clone 

3. Build

    mvn clean install

4. Generate jacoco reports (optional)

    mvn site:site

5. Run (opitional - requires local mysql with databese "school", or modify the build to use H2 see application-dev.properties)

    mvn spring-boot:run

    Browse:
        http://localhost:8080/
        or
        http://localhost:8080/swagger-ui/

6. Docker (optionl)

    docker build -t school:1.0 .

7. Docker Compose

    docker-compose up --build

# Usage

1. API

    To use POST methods on swagger-ui you need to exclude the property "id": "string", becauge it wil be generated.
