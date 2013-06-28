package com.bitresolution.xtest.core.spring.context;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DefaultAnnotationConfigApplicationContextFactory implements AnnotationConfigApplicationContextFactory {

    @Override
    public AnnotationConfigApplicationContext create() {
        return new AnnotationConfigApplicationContext();
    }
}
