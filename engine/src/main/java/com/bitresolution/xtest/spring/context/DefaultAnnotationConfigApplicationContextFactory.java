package com.bitresolution.xtest.spring.context;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.validation.constraints.NotNull;

public class DefaultAnnotationConfigApplicationContextFactory implements AnnotationConfigApplicationContextFactory {

    @Override
    @NotNull
    public AnnotationConfigApplicationContext create() {
        return new AnnotationConfigApplicationContext();
    }
}
