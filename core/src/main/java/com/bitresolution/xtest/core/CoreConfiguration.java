package com.bitresolution.xtest.core;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@ComponentScan(basePackages = "com.bitresolution.xtest")
public class CoreConfiguration {

    @Bean
    public static PropertyPlaceholderConfigurer properties() {
        PropertyPlaceholderConfigurer properties = new PropertyPlaceholderConfigurer();
        ClassPathResource[] resources = new ClassPathResource[] {new ClassPathResource("default.properties")};
        properties.setLocations(resources);
        properties.setIgnoreUnresolvablePlaceholders(true);
        return properties;
    }
}
