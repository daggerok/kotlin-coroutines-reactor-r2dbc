# kotlin-coroutines-reactor-r2dbc [![Build Status](https://travis-ci.org/daggerok/kotlin-coroutines-reactor-r2dbc.svg?branch=master)](https://travis-ci.org/daggerok/kotlin-coroutines-reactor-r2dbc)
Kotlin coroutines Reactor Spring Boot Webflux R2DBC Postgres Flyway Maven example.

## getting started

```bash
./mvnw docker:start

./mvnw process-resources flyway:migrate

./mvnw
java -jar target/*-SNAPSHOT.jar

# regular kotlin webflux
http :8080/employees name=ololo salary=123
http :8080/employees

# kotlin-coroutines-reactor api
http :8080/api/coroutines/employees name=ololo salary=123
http :8080/api/coroutines/employees/1
http :8080/api/coroutines/employees/not-found

./mvnw docker:stop
```

NOTE: _This project has been based on [GitHub: daggerok/main-starter](https://github.com/daggerok/main-starter)_

Motivated by this: [YouTube: Spring Tips: Spring's Support for Kotlin Coroutines](https://www.youtube.com/watch?v=3xu5xttCrVk)
