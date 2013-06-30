package com.bitresolution.xtest.core.phases.sources

import spock.lang.Specification


class DefaultSourceBuilderFactorySpec extends Specification {

    def "should create SourceBuilder"() {
        given:
        DefaultSourceBuilderFactory factory = new DefaultSourceBuilderFactory()

        when:
        SourceBuilder builder = factory.create()

        then:
        assert builder instanceof SourceBuilder
    }

    def "should create new SourceBuilder at each invocation"() {
        given:
        DefaultSourceBuilderFactory factory = new DefaultSourceBuilderFactory()

        when:
        SourceBuilder builderA = factory.create()
        SourceBuilder builderB = factory.create()

        then:
        assert builderA instanceof SourceBuilder
        assert builderB instanceof SourceBuilder
        assert builderA != builderB
    }
}
