package com.bitresolution.xtest.core.phases.sources

import spock.lang.Specification


class DefaultSourceBuilderSpec extends Specification {

    def "should build empty sources if no included classes"() {
        given:
        SourceBuilder builder = new DefaultSourceBuilder()

        when:
        Sources sources = builder.build()

        then:
        assert sources.empty
        assert sources.size() == 0
        assert sources.classes == [] as Set
    }
}
