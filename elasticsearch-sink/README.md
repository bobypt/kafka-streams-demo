# start
 - docker-compose up -d
 - create an index in elastic search by running postman create index.
 - Create kafka connect tink to elastic search
 - push messages to kafka topic by running jmeter


# console consumer
```
docker-compose exec kafka kafka-console-consumer.sh --bootstrap-server kafka:9092 --topic transactions --from-beginning --property print.key=true

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
#Elastic search

```
curl -s "http://localhost:9200/" | jq

```
