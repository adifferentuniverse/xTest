package com.bitresolution.xtest;

import com.bitresolution.succor.junit.category.Integration;
import com.bitresolution.succor.junit.rules.spring.SpringInjectionRule;
import com.bitresolution.xtest.core.CoreConfiguration;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.TestRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

@ContextConfiguration(classes = CoreConfiguration.class)
@Category(Integration.class)
public class EngineTest {

    @Autowired
    ApplicationContext context;

    @Rule
    public TestRule contextRule = new SpringInjectionRule(this);

    @Test
    public void contextShouldNotBeNull() {
        assertThat(context, is(not(nullValue())));
    }
}
