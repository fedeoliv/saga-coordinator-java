# Configuring Application Properties

The `application.yaml` file is located on `src/main/resources`. Make sure you updated the following `cosmosdb` and `redis` properties:

```yaml
cosmosdb:
  connectionString: <your_connection_string>
  database: <database_name>
  collection: <collection_name>
  key: <key_name>
  
redis:
    host: localhost
    port: 6379
    password: <your_password>
    ssl: false
```

For Spring Cloud Stream make sure you updated the Kafka input (consumer) and output (producer) topic names, consumer group name, schema registry URL and number of concurrent message consumers. 

```yaml
spring:
  cloud:
    stream:
      default: 
        producer:
          useNativeEncoding: true
        consumer:  
          useNativeEncoding: true     
      bindings:
        input:
          destination: my-input-topic
          content-type: application/*+avro
          group: my-consumer-group
          consumer:
            concurrency: 3
            autoCommitOffset: false
        output:
          destination: my-output-topic
          content-type: application/*+avro
      kafka:
          binder:      
            producer-properties:
              key.serializer: io.confluent.kafka.serializers.KafkaAvroSerializer
              value.serializer: io.confluent.kafka.serializers.KafkaAvroSerializer
              schema.registry.url: http://localhost:8081 
            consumer-properties:
              key.deserializer: io.confluent.kafka.serializers.KafkaAvroDeserializer
              value.deserializer: io.confluent.kafka.serializers.KafkaAvroDeserializer
              schema.registry.url: http://localhost:8081
              specific.avro.reader: true
``` 
