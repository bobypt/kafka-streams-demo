#!/bin/bash

docker-compose up -d
#try 50 times with 2s delay
HEALTH_MAX_ATTEMPT=50

# parameter 1 - health url
function waitForHealthy {
echo "Checking server status => " $1
local var attempt_counter=0
while ! $(curl --output /dev/null --silent --fail $1); do 
    if [ ${attempt_counter} -eq ${HEALTH_MAX_ATTEMPT} ];then
      echo "Max attempts reached"
      exit 1
    fi 
    sleep 2
    echo -n "."
    attempt_counter=$(($attempt_counter+1))
done
}

echo "Checking elastic search server status"
waitForHealthy "http://localhost:9200/"
echo "Elastic search server healthy. Create index."

curl -X PUT \
  http://localhost:9200/transactions_index \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 6ffcc009-acab-169d-f841-30d0059b1024' \
  -d '{
  "mappings": {
    "kafka-connect": {
      "properties": {
        "timestamp": {
          "type":   "date",
          "format": "epoch_millis"
        },
        "amount": {
        	"type":   "double"
        }
      }
    }
  }
}'

echo "Elastic search index created"

echo "Checking kafka-connect server status"
waitForHealthy "http://localhost:8083/"
echo "Kafka-connect server healthy. Create connect sink."

curl -X POST \
  http://localhost:8083/connectors \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 8ea4d2a0-86e5-502c-2bfc-3de7e38a9e83' \
  -d '{
	"name": "es-sink-connector",
  "config": {
    "connector.class": "io.confluent.connect.elasticsearch.ElasticsearchSinkConnector",
    "connection.url": "http://elasticsearch:9200",
    "type.name": "kafka-connect",
    "topics": "transactions",
    "topic.index.map": "transactions:transactions_index",    
    "key.ignore": "true",
    "schema.ignore": "true",
    "value.converter": "org.apache.kafka.connect.json.JsonConverter",
    "value.converter.schemas.enable": "false",
	"key.converter": "org.apache.kafka.connect.json.JsonConverter",
	"key.converter.schemas.enable": "false"    
  }	
}'

echo "COMPLETE.."