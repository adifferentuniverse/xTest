package com.bitresolution.xtest;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "com.bitresolution.xtest.phases",
        includeFilters = @ComponentScan.Filter(value = Configuration.class, type = FilterType.ANNOTATION)
)
public class DefaultPhaseContext {
}
