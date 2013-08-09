package com.bitresolution.xtest.core;

import com.bitresolution.xtest.core.PhaseConfiguration;
import com.bitresolution.xtest.core.XTestConfiguration;
import com.bitresolution.xtest.core.lifecycle.Phase;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

public class PhaseConfigurationBeanFactory {

    public void registerBeans(XTestConfiguration configuration, GenericApplicationContext context) throws BeansException {
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        for(PhaseConfiguration phaseConfiguration : configuration.getPhaseConfigurations().values()) {
            beanFactory.registerSingleton(buildPhaseConfigurationBeanName(phaseConfiguration), phaseConfiguration);
        }
    }

    private String buildPhaseConfigurationBeanName(PhaseConfiguration phaseConfiguration) {
        return phaseConfiguration.getPhase().getSimpleName() + "Configuration";
    }
}
