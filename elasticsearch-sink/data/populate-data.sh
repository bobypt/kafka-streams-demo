#!/bin/bash -x

TEMP_DIR=temp_jmeter_pepperbox
mkdir $TEMP_DIR
pushd $TEMP_DIR

git clone https://github.com/GSLabDev/pepper-box.git

pushd pepper-box
git checkout jmeter3.2_kafka0.11.0.0
mvn clean install -Djmeter.version=3.2 -Dkafka.version=0.11.0.0

popd
popd

cp $TEMP_DIR/pepper-box/target/pepper-box-1.0.jar .

echo "Cleanup"
rm -rf $TEMP_DIR


docker build -t jmeter-kafka .
docker run --network host jmeter-kafka:latest
