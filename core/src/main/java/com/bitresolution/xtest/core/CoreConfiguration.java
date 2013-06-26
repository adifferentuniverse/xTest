package com.bitresolution.xtest.core;

import com.bitresolution.succor.concurrent.GroupNamedThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@ComponentScan(basePackages = "com.bitresolution.xtest")
public class CoreConfiguration {

    @Bean(destroyMethod = "shutdown")
    public ExecutorService publisherExecutor() {
        return Executors.newFixedThreadPool(1, new GroupNamedThreadFactory("publisher"));
    }
}
