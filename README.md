# Start Kafka, zookeeper, schema registry etc
```
cd docker-compose
docker-compose up -d
```

# console consumer
```
docker-compose exec kafka1 kafka-console-consumer --bootstrap-server kafka1:29091 --topic test_out --from-beginning --property print.key=true
```

