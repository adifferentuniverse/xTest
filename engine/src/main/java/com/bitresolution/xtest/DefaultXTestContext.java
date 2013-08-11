package com.bitresolution.xtest;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;

@Configuration
@Import({DefaultPhaseContext.class, DefaultLifecycleContext.class, EventBusContext.class})
public class DefaultXTestContext {

    @Bean
    public static PropertyPlaceholderConfigurer properties() {
        PropertyPlaceholderConfigurer properties = new PropertyPlaceholderConfigurer();
        ClassPathResource[] resources = new ClassPathResource[] {new ClassPathResource("default.properties")};
        properties.setLocations(resources);
        properties.setIgnoreUnresolvablePlaceholders(true);
        return properties;
    }
}
