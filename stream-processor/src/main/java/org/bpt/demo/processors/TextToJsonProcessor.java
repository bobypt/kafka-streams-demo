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

import static org.bpt.demo.Util.getRandomNumber;

@Component
@Slf4j
public class TextToJsonProcessor {
    @StreamListener
    @SendTo(StreamBinding.DATA_JSON_OUT)
    public KStream<String, User> process(@Input(StreamBinding.DATA_TEXT_IN)KStream<String, String> streamIn) {
        return streamIn
                .map((k,v) -> {
                    log.info("Key:" + k +  ",Value:" + v);
                    User user = User.builder()
                            .name(v)
                            .age(getRandomNumber())
                            .email("email" + getRandomNumber() + "@email.com")
                            .build();

                    return new KeyValue<>(k, user);
                });


    }
}
