package com.bitresolution.xtest.cli

import com.beust.jcommander.ParameterException
import com.bitresolution.succor.reflection.FullyQualifiedClassName
import spock.lang.Specification


class FullyQualifiedClassNameConverterSpec extends Specification {

    def "should create FullyQualifiedClassName from argument"() {
        given:
        FullyQualifiedClassNameConverter converter = new FullyQualifiedClassNameConverter()

        when:
        FullyQualifiedClassName fqcn = converter.convert("com.bitresolution.xtest.cli.FullyQualifiedClassNameConverter")

        then:
        assert fqcn == new FullyQualifiedClassName(FullyQualifiedClassNameConverter)
    }

    def "should throw exception if error creating FullyQualifiedClassName from argument"() {
        given:
        FullyQualifiedClassNameConverter converter = new FullyQualifiedClassNameConverter()

        when:
        converter.convert(null)

        then:
        thrown(ParameterException)
    }
}
