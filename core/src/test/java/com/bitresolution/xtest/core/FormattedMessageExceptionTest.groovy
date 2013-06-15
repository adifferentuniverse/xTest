package com.bitresolution.xtest.core

import com.bitresolution.xtest.commons.FormattedMessageException
import spock.lang.Specification


class FormattedMessageExceptionTest extends Specification {

    def "should be able to create exception with message"() {
        given:
        def e = new FormattedMessageException("message")

        expect:
        assert e.message == "message"
        assert e.cause == null
    }

    def "should be able to create exception with message and cause"() {
        given:
        def e = new FormattedMessageException("message", new RuntimeException())

        expect:
        assert e.message == "message"
        assert e.cause instanceof RuntimeException
    }

    def "should be able to create exception with parameterised message"() {
        given:
        def e = new FormattedMessageException("message with parameter = {}", 6)

        expect:
        assert e.message == "message with parameter = 6"
        assert e.cause == null
    }

    def "should be able to create exception with parameterised message and cause"() {
        given:
        def e = new FormattedMessageException("message with parameter = {}", 6, new RuntimeException())

        expect:
        assert e.message == "message with parameter = 6"
        assert e.cause instanceof RuntimeException
    }
}
