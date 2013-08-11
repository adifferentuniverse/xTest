package com.bitresolution.xtest;

import com.bitresolution.xtest.core.lifecycle.Lifecycle;
import com.bitresolution.xtest.core.lifecycle.LifecycleContext;
import com.bitresolution.xtest.core.lifecycle.LifecycleExecutorException;
import com.bitresolution.xtest.phases.sources.SourceConfiguration;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;


@Configuration
@Import({LifecycleContext.class, EventBusContext.class})
public class EngineTestConfiguration {

    private final Lifecycle lifecycle;
    private final XTestConfiguration configuration;

    public EngineTestConfiguration() throws LifecycleExecutorException {
        this.lifecycle = buildLifecycle();
        this.configuration = buildConfiguration();
    }

    @Bean
    public static PropertyPlaceholderConfigurer properties() {
        PropertyPlaceholderConfigurer properties = new PropertyPlaceholderConfigurer();
        ClassPathResource[] resources = new ClassPathResource[] {new ClassPathResource("default.properties")};
        properties.setLocations(resources);
        properties.setIgnoreUnresolvablePlaceholders(true);
        return properties;
    }

    @Bean
    public Lifecycle lifecycle() throws LifecycleExecutorException {
        return lifecycle;
    }

    @Bean
    public XTestConfiguration xTestConfiguration() {
        return configuration;
    }

    private Lifecycle buildLifecycle() throws LifecycleExecutorException {
        Lifecycle lifecycle = new Lifecycle();
        MockPhase<Void, Integer> phaseA = new MockPhase<Void, Integer>(Void.class, Integer.class, 1);
        MockPhase<Integer, Integer> phaseB = new MockPhase<Integer, Integer>(Integer.class, Integer.class, 2);
        MockPhase<Integer, String> phaseC = new MockPhase<Integer, String>(Integer.class, String.class, "3");

        lifecycle.add(phaseA);
        lifecycle.add(phaseB);
        lifecycle.add(phaseC);

        return lifecycle;
    }

    private XTestConfiguration buildConfiguration() {
        XTestConfiguration c = new XTestConfiguration();
        c.addPhaseConfiguration(new SourceConfiguration());
        return c;
    }
}
