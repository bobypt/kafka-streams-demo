# console producer
```
docker-compose exec kafka kafka-console-producer.sh --broker-list kafka:9092 --topic data-in
```

{"name":"boby","address":"123 some street"}


# console consumer
```
docker-compose exec kafka kafka-console-consumer.sh --bootstrap-server kafka:9092 --topic data-in --from-beginning --property print.key=true

```

# Connect to ksql client 
```
docker-compose exec ksql-cli ksql http://ksql-server:8088

show topics;

print 'data-in';

show streams;

CREATE STREAM users_stream (name varchar, address varchar) WITH(kafka_topic='data-in', value_format='JSON');

DROP STREAM USERS_STREAM;

select * from USERS_STREAM LIMIT 3;
```