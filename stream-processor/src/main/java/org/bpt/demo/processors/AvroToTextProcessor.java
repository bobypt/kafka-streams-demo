package org.bpt.demo.processors;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.bpt.avro.User;
import org.bpt.demo.config.StreamBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AvroToTextProcessor {

    @StreamListener
    @SendTo(StreamBinding.DATA_TEXT_SINK_OUT)
    public KStream<String, String> process(@Input(StreamBinding.DATA_AVRO_IN)KStream<String, User> streamIn) {
        return streamIn
                .map((k,v) -> {
                    log.info("Key:" + k +  ",User name:" + v.getName());
                    return new KeyValue<>(k, v.getName());
                });
    }

}
