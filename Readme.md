# Workshop assignment

## About the assignment

This is the pair-programming/workshop for senior interviews at Brandwatch.

Please consider this codebase as if you had received it for a code review.

You should read through the task/project description below, then start to get familiar with the codebase.

Identify issues, and discuss potential solutions to those issues with the interviewers as you would with teammates.
Some solutions can be done by adding/modifying a few lines of code, we would ask you to do so while sharing your screen.
Other, larger solutions don't need implementation, just discuss it like you would discuss it with a colleague.

## Project description

This application is part of a larger development, its main purpose is connecting to a 3rd party API, store the data it received, and the ability to create reports based on it.

The application is also expected to send out messages based on reports, since that is needed by a downstream system to make sure that customer billing is kept up-to-date.
Initially, the only report that is supported will be calculating the averages over a period of time.

## Helpful commands

### Application startup

In the docker folder:
`docker compose up -d`

Then add the provided API-key to the service, and start up the project in your preferred way.

### Kafka

Listen on sent out Kafka messages:

`docker run -it --rm --network docker_weather-app confluentinc/cp-kafka /bin/kafka-console-consumer --bootstrap-server kafka:9092 --topic report-events`

### Sending messages

See assigned postman collection, use the sprindoc frontend at http://localhost:8080/swagger-ui/index.html, or use CURL:

```shell
curl --location 'http://localhost:8080/weather/Budapest'
curl --location --request POST 'http://localhost:8080/weather/Budapest/delete'
curl --location --request POST 'http://localhost:8080/weather/Budapest/average?from=2023-12-12&to=2023-12-14&userId=myId'
curl --location 'http://localhost:8080/report?city=Budapest&user=myId'
```

