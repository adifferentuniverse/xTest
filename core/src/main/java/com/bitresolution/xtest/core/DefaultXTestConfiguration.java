package com.bitresolution.xtest.core;

import com.bitresolution.succor.concurrent.GroupNamedThreadFactory;
import com.bitresolution.xtest.core.lifecycle.Lifecycle;
import com.bitresolution.xtest.core.lifecycle.LifecycleExecutorException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@Import({CoreConfiguration.class})
public class DefaultXTestConfiguration {

    @Bean
    public Lifecycle lifecycle() throws LifecycleExecutorException {
        return new Lifecycle();
    }

    @Bean(destroyMethod = "shutdown")
    public ExecutorService publisherExecutor(@Value("${xtest.events.publisher.threads:1}") Integer threadCount) {
        return Executors.newFixedThreadPool(threadCount, new GroupNamedThreadFactory("publisher"));
    }
}
