# Workshop assignment

## About the assignment

This is the pair-programming/workshop for senior interviews at Brandwatch.

Please consider this codebase as if you had received it for a code review.

You should read through the task/project description below, then start to get familiar with the codebase.

Identify issues, and discuss potential solutions to those issues with the interviewers as you would with teammates.
Some solutions can be done by adding/modifying a few lines of code, we would ask you to do so while sharing your screen.
Other, larger solutions don't need implementation, just discuss it like you would discuss it with a colleague.

You don't need to check for creating new, useful endpoints or other domain-changes, we are interested in what code or architectural changes would you make in the repository.

## Project description

The IndexingApplication's purpose is to load data from 2 imaginary social networks - the DadJokes and the ChuckNorrisJokes sites, and match the new contents with those that our users are interested in.
It's indexing API will be called periodically from another service, while the users can call other APIs to get all stored contents, and create or delete their own queries.

In case it finds a content that one of our users is interested in, it sends out a Kafka message to notify downstream systems of the match.

## Helpful commands

### Application startup

In the docker folder:
`docker compose up -d`

Then add the provided API-key to the service, and start up the project in your preferred way.

### Kafka

Listen on sent out Kafka messages:

`docker run -it --rm --network docker_indexing-app confluentinc/cp-kafka /bin/kafka-console-consumer --bootstrap-server kafka:9092 --topic match-events`

### Sending messages

Use the springdoc frontend at http://localhost:8080/swagger-ui/index.html
