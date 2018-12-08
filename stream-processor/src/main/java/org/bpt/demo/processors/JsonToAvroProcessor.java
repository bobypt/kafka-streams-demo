package org.bpt.demo.processors;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.bpt.demo.config.StreamBinding;
import org.bpt.demo.model.User;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class JsonToAvroProcessor {
    @StreamListener
    @SendTo(StreamBinding.DATA_AVRO_OUT)
    public KStream<String, org.bpt.avro.User> process(@Input(StreamBinding.DATA_JSON_IN)KStream<String, User> streamIn) {
        return streamIn
                .map((k,v) -> {
                    log.info("Key:" + k +  ",User name:" + v.getName());
                    org.bpt.avro.User user = org.bpt.avro.User.newBuilder()
                            .setName(v.getName())
                            .setAge(v.getAge())
                            .setEmail(v.getEmail())
                            .build();
                    return new KeyValue<>(k, user);
                });


    }
}
