package com.bitresolution.xtest.spring.beans;

import com.bitresolution.succor.junit.category.Unit;
import com.bitresolution.xtest.MockPhase;
import com.bitresolution.xtest.MockPhaseConfiguration;
import com.bitresolution.xtest.XTestConfiguration;
import groovy.lang.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.GenericApplicationContext;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@Category(Unit.class)
@RunWith(PowerMockRunner.class)
@PrepareForTest(GenericApplicationContext.class)
public class PhaseConfigurationFactorySpec {

    @Mock
    GenericApplicationContext context;
    @Mock
    ConfigurableListableBeanFactory beanFactory;

    PhaseConfigurationBeanFactory factory;

    @Before
    public void setup() {
        factory = new PhaseConfigurationBeanFactory();
        PowerMockito.when(context.getBeanFactory()).thenReturn(beanFactory);
    }

    @Test
    public void shouldDoNothingIfNoPhaseConfigurationsPresent() {
        //when
        factory.registerBeans(new XTestConfiguration(), context);

        //then
        verify(context).getBeanFactory();
        verifyNoMoreInteractions(context);
    }

    @Test
    public void shouldRegisterPhaseConfigurations() {
        //given:
        XTestConfiguration configuration = new XTestConfiguration();
        MockPhaseConfiguration phaseConfiguration = new MockPhaseConfiguration(MockPhase.class);
        configuration.addPhaseConfiguration(phaseConfiguration);

        //when:
        factory.registerBeans(configuration, context);

        //then:
        verify(context).getBeanFactory();
        verify(beanFactory).registerSingleton(anyString(), eq(phaseConfiguration));
        verifyNoMoreInteractions(context);
    }

}
