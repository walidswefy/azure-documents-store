Sample API Provider
====================

## What is that?

This project is a code baseline that can be used to quickly develop modern API-based applications 
that can fit within Monolithic or Microservices architecture.

## Technology Stack

- `java`, `spring-boot`, `spring-web`
  [core]
- `mongodb`, `flapdoodle`
  [database]
- `git`, `maven`
  [code & build]
- `logback`, `filebeat`, `elastic-search`, `kibana`
  [log management]
- `swagger`, `http-client`, `junit`, `mockito`
  [testing] 
- `spring-actuator`, `prometheus`, `grafana`
  [monitoring]   
- `lombok`, `devtools`
  [development] 
- `docker`, `docker-compose`
  [containers]

## How to run

compile application:

```bash
mvn clean package
```

build docker image:

```bash
mvn com.google.cloud.tools:jib-maven-plugin:2.4.0:dockerBuild
```

run [application](http://localhost:8012/) or browse [swagger](http://localhost:8012/swagger-ui.html) documentation:

```bash
docker-compose -f app.yml up -d
```

check [kibana](http://localhost:5601) for logs:

```bash
docker-compose -f elk.yml up -d
```

view [grafana](http://localhost:3000) dashboard to monitor application:

```bash
docker-compose -f monitoring.yml up -d
```

run [sonar](http://localhost:9001) to check code quality and test coverage:

```bash
docker-compose -f sonar.yml up -d
mvn clean package -P dev,sonar
mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar -P dev,sonar
```

### application profiles

* **default**: localhost run
* **dev, qa, uat, prod**: dedicated profile for each environment you have
* **elk**: print logs to console in JSON format that can be efficiently shipped to elastic search
* **tls**: for testing secure connections using self-signed certificates

### build profiles

* **dev**: default profile, activates spring-boot-devtools for development environment
* **sonar**: activates jacoco for generating code coverage reports, and sonar for code feedback

### Unit & Integration Testing
Unit tests cover testing of individual components. 
External dependencies are usually mocked to isolate tested units.

Integration tests (classes tagged with `@it` or `@integration`) cover end-to-end testing.
The application uses in memory database for testing database integration.

> Integration tests are slow and run separately using different build cycle (maven verify) 

### Maven Tools
Maven utilizes The following list of plugins and dependencies

* **sonar**: code quality
* **jacoco**: code coverage reports
* **jib**: building docker image
* **git-commit-id**: generating git.properties within application artifact to track the code version
* **surefire**: unit testing (`mvn test`)
* **fail-safe**: integration testing (`mvn verify`)
* **jasypt**: encrypting sensitive profile values (mainly in prod profile)

### References

[#JHipster](https://www.jhipster.tech/)
[#Elastic](https://www.elastic.co/)
[#Docker](https://www.docker.com/)
[#Grafana](https://grafana.com/)
