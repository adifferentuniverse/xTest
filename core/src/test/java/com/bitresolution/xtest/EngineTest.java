package com.bitresolution.xtest;

import com.bitresolution.xtest.core.lifecycle.Lifecycle;
import com.bitresolution.xtest.core.lifecycle.LifecycleExecutor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.VerboseMockitoJUnitRunner;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(VerboseMockitoJUnitRunner.class)
public class EngineTest {

    @Mock
    private Lifecycle lifecycle;
    @Mock
    private LifecycleExecutor executor;
    @Mock
    private AnnotationConfigApplicationContext context;

    @Test
    public void shouldExecuteLifecycle() throws Exception {
        //given
        given(context.getBean(LifecycleExecutor.class)).willReturn(executor);
        Engine engine = new Engine(){
            @Override
            protected Lifecycle buildLifecycle() {
                return lifecycle;
            }

            @Override
            protected AnnotationConfigApplicationContext buildApplicationContext(Class<?> configurationClass) {
                return context;
            }
        };

        //when
        engine.run();

        //then
        verify(executor).execute(lifecycle);
    }

    @Test
    public void shouldShutdownWithErrorIfNoLifecycleExecutorFound() {
        //given
        given(context.getBean(LifecycleExecutor.class)).willThrow(new NoSuchBeanDefinitionException(LifecycleExecutor.class));
        Engine engine = new Engine(){
            @Override
            protected AnnotationConfigApplicationContext buildApplicationContext(Class<?> configurationClass) {
                return context;
            }
        };

        //when
        engine.run();

        //then no exception thrown
        verify(context).getBean(LifecycleExecutor.class);
        verifyNoMoreInteractions(context);
    }
}
