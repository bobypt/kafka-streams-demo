package org.bpt.demo.feed;

import org.bpt.demo.config.StreamBinding;
import org.bpt.demo.model.User;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Component
public class DataFeedService {

    private final MessageChannel userData;

    public DataFeedService(StreamBinding binding) {
        this.userData = binding.userDataOut();
    }

    public void send() {

        User user = new User("Name" + getRandomNumber(), "email"+getRandomNumber()+"@email.com.au", getRandomNumber());
        Message<User> message = MessageBuilder
                .withPayload(user)
                .setHeader(KafkaHeaders.MESSAGE_KEY, UUID.randomUUID().toString().getBytes())
                .build();

        this.userData.send(message);
    }

    private int getRandomNumber() {
        Random ran = new Random();
        return ran.nextInt(100);
    }
}
