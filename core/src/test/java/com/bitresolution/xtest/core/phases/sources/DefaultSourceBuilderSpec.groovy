package com.bitresolution.xtest.core.phases.sources

import com.bitresolution.succor.reflection.FullyQualifiedClassName
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

    def "should include sources if no excluded classes"() {
        given:
        SourceBuilder builder = new DefaultSourceBuilder()
        builder.includeClassSources([new ClassSource(SourceBuilder)])

        when:
        Sources sources = builder.build()

        then:
        assert !sources.empty
        assert sources.size() == 1
        assert sources.classes == [new FullyQualifiedClassName(SourceBuilder)] as Set
    }
}
