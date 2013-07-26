package com.bitresolution.xtest.core;

import com.bitresolution.succor.junit.category.Integration;
import com.bitresolution.succor.spring.junit.rules.SpringInjectionRule;
import com.bitresolution.xtest.core.lifecycle.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.TestRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

@ContextConfiguration(classes = EngineTestConfiguration.class)
@Category(Integration.class)
public class EngineTest {

    @Autowired
    Lifecycle lifecycle;
    @Autowired
    LifecycleExecutor lifecycleExecutor;

    @Rule
    public TestRule contextRule = new SpringInjectionRule(this);

    @Test
    public void shouldExecuteLifecycle() throws LifecycleExecutorException {
        //given
        assumeThat(lifecycle, is(not(nullValue())));
        assumeThat(lifecycleExecutor, is(not(nullValue())));

        //when
        lifecycleExecutor.execute();

        //then
        for(Phase<?, ?> phase : lifecycle) {
            assertThat(((MockPhase<?, ?>)phase).isExecuted(), is(true));
        }
    }

}
