package com.bitresolution.xtest;

import com.bitresolution.succor.concurrent.GroupNamedThreadFactory;
import com.bitresolution.xtest.core.lifecycle.Lifecycle;
import com.bitresolution.xtest.core.lifecycle.LifecycleExecutorException;
import com.bitresolution.xtest.eventbus.NonBlockingPublisher;
import com.bitresolution.xtest.eventbus.Publisher;
import com.bitresolution.xtest.eventbus.Subscriber;
import com.bitresolution.xtest.phases.compile.CompileGraphPhase;
import com.bitresolution.xtest.phases.execute.ExecuteFixturesPhase;
import com.bitresolution.xtest.phases.generate.CompileFixturesPhase;
import com.bitresolution.xtest.phases.reporting.ProcessReportPhase;
import com.bitresolution.xtest.phases.sources.GenerateSourcesPhase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@Import(value = {
        DefaultPhaseContext.class,
        DefaultLifecycleContext.class
})
public class XTestStandardContext {

    @Autowired(required = false)
    private List<Subscriber> subscribers = new ArrayList<Subscriber>();

    @Bean
    public static PropertyPlaceholderConfigurer properties() {
        PropertyPlaceholderConfigurer properties = new PropertyPlaceholderConfigurer();
        ClassPathResource[] resources = new ClassPathResource[] {new ClassPathResource("default.properties")};
        properties.setLocations(resources);
        properties.setIgnoreUnresolvablePlaceholders(true);
        return properties;
    }

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
