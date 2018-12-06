package org.bpt.demo;

import org.bpt.demo.config.StreamBinding;
import org.bpt.demo.model.User;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class DataFeedService {

    private final MessageChannel userData;

    public DataFeedService(StreamBinding binding) {
        this.userData = binding.userDataOut();
    }

    public void send() {
        Random ran = new Random();
        User user = new User("Boby", "boby@boby.com.au", ran.nextInt(100));
        Message<User> message = MessageBuilder
                .withPayload(user)
                .setHeader(KafkaHeaders.MESSAGE_KEY, "Boby".getBytes())
                .build();

        this.userData.send(message);
    }
}
