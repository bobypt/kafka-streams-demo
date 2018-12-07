package org.bpt.demo.processors;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.bpt.demo.config.StreamBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InputToTextProcessor {

    @StreamListener
    public void process(@Input (StreamBinding.USER_DATA_IN)KStream<String, String> userKStream) {
        userKStream
                .foreach((key, value) -> {
                    log.error(key + value.toString());
                });
    }
}
