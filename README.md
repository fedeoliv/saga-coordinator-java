# Saga Coordinator as a Finite State Machine (FSM) in Java

## Background

Coordination is a [Saga](https://microservices.io/patterns/data/saga.html) approach where the coordination logic of business processes (e.g. financial transactions) is centralized in a *Saga Coordinator*, also known as *Reactive Orchestrator*. It uses the concept of **commands** and **events**, where commands are tasks that need to be done and events are tasks that have been done. 

The Saga Coordinator produces commands to an event stream and the respective saga participants consume these commands. These participants produce their events after performing their operations and the coordinator consume these Event.

![Saga Coordinator](./images/saga-coordinator.jpg)

The Saga Coordinator can be implemented as a Finite State Machine (FSM), where it produces commands and waits for events produced by the participants to drive the flow. Events are represented as nodes, while commands are represented as transitions between nodes.

## About the sample

The sample contains an FSM Saga Coordinator using [Spring Statemachine](https://projects.spring.io/spring-statemachine/) as the foundation and [Apache Kafka](https://kafka.apache.org/intro.html) as the distributed streaming platform. The state machine has the following components and features:

- [Spring Cloud Stream](https://cloud.spring.io/spring-cloud-stream-binder-kafka/) with Kafka binder to allow transparent connectivity with Kafka topics
- [Apache Avro](https://avro.apache.org/) for data serialization
- [Redis](https://redis.io/) to persist the state machine context
- [MongoDb](https://www.mongodb.com/) to persist all payloads used in transitions between nodes

## Prerequisites

- Java JDK 8 or later
- Maven 3.0 or later
- Confluent Kafka + Zookeper + Schema Registry services running. If you want to run locally as Docker containers, follow the instructions [here](https://hub.docker.com/r/confluent/kafka).
- Redis instance running. If you want to run locally as Docker container, follow the instructions [here](https://hub.docker.com/_/redis/).
- MongoDB instance running. If you want to run locally as Docker container, follow the instructions [here](https://hub.docker.com/_/mongo).

## Getting Started

The lab is divided in the following steps:

- [Step 1: Understanding the workflow](./docs/workflow.md)
- [Step 2: Updating Application Properties]() (WIP)
- [Step 3: Build and run as Docker container]() (WIP)
