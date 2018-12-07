package org.bpt.demo.config;

import org.apache.kafka.streams.kstream.KStream;
import org.bpt.demo.model.User;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface StreamBinding {

    String USER_DATA_OUT = "userDataOut";
    String DATA_IN = "dataIn";
    String DATA_TEXT_OUT = "dataTextOut";
    String DATA_TEXT_IN = "dataTextIn";
    String DATA_JSON_OUT = "dataJsonOut";
    String DATA_JSON_IN = "dataJsonIn";

    @Output (USER_DATA_OUT)
    MessageChannel userDataOut();

    @Input(DATA_IN)
    KStream<String, String> dataIn();

    @Output(DATA_TEXT_OUT)
    KStream<String, String> dataTextOut();

    @Input(DATA_TEXT_IN)
    KStream<String, String> dataTextIn();

    @Output(DATA_JSON_OUT)
    KStream<String, String> dataJsonOut();

    @Input(DATA_JSON_IN)
    KStream<String, User> dataJsonIn();

}
