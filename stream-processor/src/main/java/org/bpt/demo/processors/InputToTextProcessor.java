package org.bpt.demo.processors;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.bpt.demo.config.StreamBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class InputToTextProcessor {

    @StreamListener
    @SendTo(StreamBinding.DATA_TEXT_OUT)
    public KStream<String, String> process(@Input (StreamBinding.DATA_IN)KStream<String, String> userKStream) {
        return userKStream
                .map((k,v) -> {
                    log.info("Key:" + k +  ",Value:" + v);
                    return new KeyValue<>(UUID.randomUUID().toString(), v);
                });


    }
}
