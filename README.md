# Start Kafka, zookeeper, schema registry etc
```
cd docker-compose
docker-compose up -d
```


# Sample data flow

data-in  --> data-text
data-text --> data-json
data-json --> data-avro
data-avro --> data-text-sink

# console consumer
```
docker-compose exec kafka1 kafka-console-consumer --bootstrap-server kafka1:29091 --topic test_out --from-beginning --property print.key=true
```

# console producer
```
docker-compose exec kafka1 kafka-console-producer --broker-list kafka1:29091 --topic data-in
```