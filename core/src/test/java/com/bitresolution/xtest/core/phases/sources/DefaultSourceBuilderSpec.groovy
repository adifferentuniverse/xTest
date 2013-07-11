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

    def "should include class source if no excluded classes"() {
        given:
        SourceBuilder builder = new DefaultSourceBuilder()
        builder.include([new ClassSource(SourceBuilder.canonicalName)])

        when:
        Sources sources = builder.build()

        then:
        assert !sources.empty
        assert sources.size() == 1
        assert sources.classes == [new FullyQualifiedClassName(SourceBuilder.canonicalName)] as Set
    }

    def "should include class sources if no excluded classes"() {
        given:
        SourceBuilder builder = new DefaultSourceBuilder()
        builder.include([new ClassSource(SourceBuilder.canonicalName), new ClassSource(DefaultSourceBuilder.canonicalName)])

        when:
        Sources sources = builder.build()

        then:
        assert !sources.empty
        assert sources.size() == 2
        assert sources.classes == [
                new FullyQualifiedClassName(SourceBuilder.canonicalName),
                new FullyQualifiedClassName(DefaultSourceBuilder)
        ] as Set
    }

    def "should not include class in sources if it is an excluded class"() {
        given:
        SourceBuilder builder = new DefaultSourceBuilder()
        builder.include([new ClassSource(SourceBuilder.canonicalName), new ClassSource(DefaultSourceBuilder.canonicalName)])
        builder.exclude([new ClassSource(SourceBuilder.canonicalName)])

        when:
        Sources sources = builder.build()

        then:
        assert !sources.empty
        assert sources.size() == 1
        assert sources.classes == [
                new FullyQualifiedClassName(DefaultSourceBuilder)
        ] as Set
    }

    def "should not include classes in sources if they are excluded classes"() {
        given:
        SourceBuilder builder = new DefaultSourceBuilder()
        builder.include([new ClassSource(SourceBuilder.canonicalName), new ClassSource(DefaultSourceBuilder.canonicalName)])
        builder.exclude([new ClassSource(SourceBuilder.canonicalName), new ClassSource(DefaultSourceBuilder.canonicalName)])

        when:
        Sources sources = builder.build()

        then:
        assert sources.empty
        assert sources.classes == [] as Set
    }
}
