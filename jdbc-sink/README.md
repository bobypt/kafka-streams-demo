# console producer
```
docker-compose exec kafka kafka-console-producer.sh --broker-list kafka:9092 --topic data-in
```

{"name":"boby","address":"123 some street"}


# console consumer
```
docker-compose exec kafka kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic data-in --from-beginning --property print.key=true

```