package org.bpt.demo.feed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataFeedAppRunner implements ApplicationRunner {

    @Autowired
    private DataFeedService dataFeedService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        dataFeedService.send();

    }
}
