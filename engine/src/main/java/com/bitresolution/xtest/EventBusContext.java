package com.bitresolution.xtest;

import com.bitresolution.succor.concurrent.GroupNamedThreadFactory;
import com.bitresolution.xtest.eventbus.NonBlockingPublisher;
import com.bitresolution.xtest.eventbus.Publisher;
import com.bitresolution.xtest.eventbus.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class EventBusContext {

    @Autowired(required = false)
    private List<Subscriber> subscribers = new ArrayList<Subscriber>();

    @Bean(destroyMethod = "shutdown")
    public ExecutorService publisherExecutor(@Value("${xtest.events.publisher.threads:1}") Integer threadCount) {
        return Executors.newFixedThreadPool(threadCount, new GroupNamedThreadFactory("publisher"));
    }

    @Bean
    public Publisher publisher(ExecutorService executor) {
        NonBlockingPublisher publisher = new NonBlockingPublisher(executor);
        publisher.subscribe(subscribers);
        return publisher;
    }
}
