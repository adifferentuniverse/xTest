package com.bitresolution.xtest.core;

import com.bitresolution.succor.reflection.FullyQualifiedClassName;
import com.bitresolution.xtest.core.lifecycle.EngineTestConfiguration;
import com.bitresolution.xtest.core.lifecycle.LifecycleExecutor;
import com.bitresolution.xtest.core.lifecycle.LifecycleExecutorException;
import com.bitresolution.xtest.core.spring.context.AnnotationConfigApplicationContextFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AnnotationConfigApplicationContext.class)
public class EngineSpec {

    @Mock
    private AnnotationConfigApplicationContextFactory contextFactory;
    @Mock
    private AnnotationConfigApplicationContext context;
    @Mock
    private ConfigurableListableBeanFactory beanFactory;
    @Mock
    private LifecycleExecutor lifecycleExecutor;

    FullyQualifiedClassName configClass = new FullyQualifiedClassName(EngineTestConfiguration.class);
    SourceConfiguration sourceConfiguration = new SourceConfiguration();
    Properties properties = new Properties();


    @Test
    public void shouldExitWithSuccessIfLifecycleExecutedSuccessfully() throws Exception {
        //given:
        given(contextFactory.create()).willReturn(context);
        given(context.getBeanFactory()).willReturn(beanFactory);
        given(context.getBean(Matchers.eq(LifecycleExecutor.class))).willReturn(lifecycleExecutor);

        Engine engine = new Engine(configClass, sourceConfiguration, properties, contextFactory);

        //when:
        Engine.ExitStatus status = engine.execute();

        //then:
        verify(contextFactory).create();
        verify(context).register(configClass.loadClass());
        verify(context).getBeanFactory();
        verify(beanFactory).registerSingleton("properties", properties);
        verify(context).refresh();
        verify(context).getBean(LifecycleExecutor.class);
        verify(lifecycleExecutor).execute();
        verify(context).close();
        verifyNoMoreInteractions(lifecycleExecutor, context, beanFactory, contextFactory);
        assertThat(status, is(Engine.ExitStatus.SUCCESS));
    }

    @Test
    public void shouldExitWithErrorIfLifecycleExecutorNotFound() throws Exception {
        //given:
        given(contextFactory.create()).willReturn(context);
        given(context.getBeanFactory()).willReturn(beanFactory);
        given(context.getBean(Matchers.eq(LifecycleExecutor.class))).willThrow(new NoSuchBeanDefinitionException("lifecycleExecutor"));

        Engine engine = new Engine(configClass, sourceConfiguration, properties, contextFactory);

        //when:
        Engine.ExitStatus status = engine.execute();

        //then:
        verify(contextFactory).create();
        verify(context).register(configClass.loadClass());
        verify(context).getBeanFactory();
        verify(beanFactory).registerSingleton("properties", properties);
        verify(context).refresh();
        verify(context).getBean(LifecycleExecutor.class);
        verify(context).close();
        verifyNoMoreInteractions(lifecycleExecutor, context, beanFactory, contextFactory);
        assertThat(status, is(Engine.ExitStatus.ERROR));
    }

    @Test
    public void shouldExitWithErrorIfExceptionThrownDuringLifecycleExecution() throws Exception {
        //given:
        given(contextFactory.create()).willReturn(context);
        given(context.getBeanFactory()).willReturn(beanFactory);
        given(context.getBean(Matchers.eq(LifecycleExecutor.class))).willReturn(lifecycleExecutor);
        doThrow(new LifecycleExecutorException("error")).when(lifecycleExecutor).execute();

        Engine engine = new Engine(configClass, sourceConfiguration, properties, contextFactory);

        //when:
        Engine.ExitStatus status = engine.execute();

        //then:
        verify(contextFactory).create();
        verify(context).register(configClass.loadClass());
        verify(context).getBeanFactory();
        verify(beanFactory).registerSingleton("properties", properties);
        verify(context).refresh();
        verify(context).getBean(LifecycleExecutor.class);
        verify(lifecycleExecutor).execute();
        verify(context).close();
        verifyNoMoreInteractions(lifecycleExecutor, context, beanFactory, contextFactory);
        assertThat(status, is(Engine.ExitStatus.ERROR));
    }

    @Test
    public void shouldExitWithSuccessWhenLifecycleExecutionSuccessfulEvenIfExceptionThrownDuringContextShutdown() throws Exception {
        //given:
        given(contextFactory.create()).willReturn(context);
        given(context.getBeanFactory()).willReturn(beanFactory);
        given(context.getBean(Matchers.eq(LifecycleExecutor.class))).willReturn(lifecycleExecutor);
        doThrow(new RuntimeException("error")).when(context).close();

        Engine engine = new Engine(configClass, sourceConfiguration, properties, contextFactory);

        //when:
        Engine.ExitStatus status = engine.execute();

        //then:
        verify(contextFactory).create();
        verify(context).register(configClass.loadClass());
        verify(context).getBeanFactory();
        verify(beanFactory).registerSingleton("properties", properties);
        verify(context).refresh();
        verify(context).getBean(LifecycleExecutor.class);
        verify(lifecycleExecutor).execute();
        verify(context).close();
        verifyNoMoreInteractions(lifecycleExecutor, context, beanFactory, contextFactory);
        assertThat(status, is(Engine.ExitStatus.SUCCESS));
    }
}
