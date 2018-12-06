package org.bpt.demo.config;

import org.apache.kafka.streams.kstream.KStream;
import org.bpt.demo.model.User;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface StreamBinding {

    String USER_DATA_OUT = "userDataOut";
    String USER_DATA_IN = "userDataIn";

    @Output (USER_DATA_OUT)
    MessageChannel userDataOut();

    @Input(USER_DATA_IN)
    KStream<String, User> userDataIn();

}
