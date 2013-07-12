package com.bitresolution.xtest.core.phases.sources

import com.bitresolution.succor.reflection.FullyQualifiedClassName
import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

class SourcesSpec extends Specification {

    def "should not allow null input in constructor"() {
        when:
        new Sources(null);

        then:
        thrown(NullPointerException)
    }

    def "should be empty if no souces"() {
        when:
        Set<FullyQualifiedClassName> input = []
        Sources sources = new Sources(input)

        then:
        assert sources.empty
        assert sources.size() == 0
    }

    def "should contain all souces"() {
        when:
        Set<FullyQualifiedClassName> input = [
                new FullyQualifiedClassName(Sources),
                new FullyQualifiedClassName(SourcesSpec)
        ]
        Sources sources = new Sources(input)

        then:
        assert !sources.empty
        assert sources.size() == input.size()
        assert sources.getClasses() == input
    }

    def "should not allow modification of souces"() {
        given:
        Set<FullyQualifiedClassName> input = [
                new FullyQualifiedClassName(Sources),
                new FullyQualifiedClassName(SourcesSpec)
        ]
        Sources sources = new Sources(input)

        when:
        sources.classes.add(new FullyQualifiedClassName(Object))

        then:
        thrown(UnsupportedOperationException)
    }

    def "should honor equals contract"() {
        expect:
        EqualsVerifier.forClass(Sources.class)
                .usingGetClass()
                .verify();

    }
}
