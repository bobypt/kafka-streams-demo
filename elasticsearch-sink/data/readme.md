# jmeter plugin for connecting to kafka topics
 -- Build pepperbox jar file.
``` ./build.sh ```
-- builddocker image
```docker build -t jmeter-kafka .```

docker run --network host jmeter-kafka:latest