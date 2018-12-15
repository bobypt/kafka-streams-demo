# Data flow from topic to topic

data-in --> data-text --> data-json --> data-avro --> data-text-sink

# Start Kafka, zookeeper, schema registry etc
```
cd docker-compose
docker-compose up -d
```

# console producer
```
docker-compose exec kafka1 kafka-console-producer --broker-list kafka1:29091 --topic data-in
```


# console consumer
```
--text-out
docker-compose exec kafka1 kafka-console-consumer --bootstrap-server kafka1:29091 --topic data-text --from-beginning --property print.key=true

--json-out
docker-compose exec kafka1 kafka-console-consumer --bootstrap-server kafka1:29091 --topic data-json --from-beginning --property print.key=true


--avro-out
docker-compose exec schemaregistry kafka-avro-console-consumer --bootstrap-server kafka1:29091 --topic data-avro --from-beginning --property print.key=true --property schema.registry.url="http://schema-registry:8085"


--data-text-sink
docker-compose exec kafka1 kafka-console-consumer --bootstrap-server kafka1:29091 --topic data-text-sink --from-beginning --property print.key=true

```

