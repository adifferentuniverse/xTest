package com.bitresolution.xtest.core

import com.bitresolution.succor.junit.category.Unit
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import spock.lang.Specification


@Category(Unit)
class XTestConfigurationFactorySpec extends Specification {

    def "should do nothing if no phase configurations present"() {
        given:
        ConfigurableListableBeanFactory beanFactory = Mock(ConfigurableListableBeanFactory)
        PhaseConfigurationBeanFactory factory = new PhaseConfigurationBeanFactory(new XTestConfiguration())

        when:
        factory.registerBeans(beanFactory)

        then:
        0 * _._ // no (more) method call on any mock
    }

    def "should register phase configurations"() {
        given:
        ConfigurableListableBeanFactory beanFactory = Mock(ConfigurableListableBeanFactory)
        XTestConfiguration configuration = new XTestConfiguration()
        PhaseConfiguration phaseConfiguration = Mock(PhaseConfiguration)
        configuration.addPhaseConfiguration(phaseConfiguration)
        PhaseConfigurationBeanFactory factory = new PhaseConfigurationBeanFactory(configuration)


        when:
        factory.registerBeans(beanFactory)

        then:
        1 * beanFactory.registerSingleton("GenerateSourcesPhaseConfiguration", phaseConfiguration)
    }
}
