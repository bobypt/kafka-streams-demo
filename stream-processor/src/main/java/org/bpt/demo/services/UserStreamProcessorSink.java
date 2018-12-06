package org.bpt.demo.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.bpt.demo.config.StreamBinding;
import org.bpt.demo.model.User;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserStreamProcessorSink {

    @StreamListener
    public void process(@Input (StreamBinding.USER_DATA_IN)KStream<String, User> userKStream) {
        userKStream
                .foreach((key, value) -> {
                    log.error(key + value.toString());
                });
    }
}
