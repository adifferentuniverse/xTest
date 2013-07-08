package com.bitresolution.xtest.core.phases.sources

import com.beust.jcommander.JCommander
import spock.lang.Specification


class SourceConfigurationSpec extends Specification {

    def "should configure included class from command line args"() {
        given:
        SourceConfiguration configuration = new SourceConfiguration()
        String[] args = ["-class", SourceConfiguration.canonicalName]

        when:
        new JCommander(configuration, args)

        then:
        assert configuration.includedClasses == [new ClassSource(SourceConfiguration)]
        assert configuration.excludedClasses == []
    }

    def "should configure included classes from command line args"() {
        given:
        SourceConfiguration configuration = new SourceConfiguration()
        String[] args = ["-classes", SourceConfiguration.canonicalName, SourceConfigurationSpec.canonicalName]

        when:
        new JCommander(configuration, args)

        then:
        assert configuration.includedClasses == [
                new ClassSource(SourceConfiguration),
                new ClassSource(SourceConfigurationSpec)
        ]
        assert configuration.excludedClasses == []
    }


    def "should configure excluded class from command line args"() {
        given:
        SourceConfiguration configuration = new SourceConfiguration()
        String[] args = ["-excludeclasses", SourceConfiguration.canonicalName]

        when:
        new JCommander(configuration, args)

        then:
        assert configuration.includedClasses == []
        assert configuration.excludedClasses == [new ClassSource(SourceConfiguration)]
    }

    def "should configure excluded classes from command line args"() {
        given:
        SourceConfiguration configuration = new SourceConfiguration()
        String[] args = ["-excludeclasses", SourceConfiguration.canonicalName, SourceConfigurationSpec.canonicalName]

        when:
        new JCommander(configuration, args)

        then:
        assert configuration.includedClasses == []
        assert configuration.excludedClasses == [
                new ClassSource(SourceConfiguration),
                new ClassSource(SourceConfigurationSpec)
        ]
    }
}
