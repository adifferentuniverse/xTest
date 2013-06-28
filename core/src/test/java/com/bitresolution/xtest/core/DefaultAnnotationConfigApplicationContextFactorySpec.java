package com.bitresolution.xtest.core;

import com.bitresolution.xtest.core.spring.context.AnnotationConfigApplicationContextFactory;
import com.bitresolution.xtest.core.spring.context.DefaultAnnotationConfigApplicationContextFactory;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DefaultAnnotationConfigApplicationContextFactorySpec {

    @Test
    public void shouldReturnNewAnnotationConfigApplicationContext() {
        //given
        AnnotationConfigApplicationContextFactory factory = new DefaultAnnotationConfigApplicationContextFactory();

        //when
        AnnotationConfigApplicationContext contextA = factory.create();
        AnnotationConfigApplicationContext contextB = factory.create();

        //then
        assertThat(contextA, is(instanceOf(AnnotationConfigApplicationContext.class)));
        assertThat(contextB, is(instanceOf(AnnotationConfigApplicationContext.class)));
        assertThat(contextA, is(not(contextB)));
    }
}
