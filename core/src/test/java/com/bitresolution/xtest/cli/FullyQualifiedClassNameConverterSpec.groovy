package com.bitresolution.xtest.cli

import com.beust.jcommander.ParameterException
import com.bitresolution.succor.reflection.FullyQualifiedClassName
import com.bitresolution.xtest.core.phases.sources.ClassSource
import spock.lang.Specification


class FullyQualifiedClassNameConverterSpec extends Specification {

    def "should create FullyQualifiedClassName from argument"() {
        given:
        ClassSourceConverter converter = new ClassSourceConverter()

        when:
        ClassSource fqcn = converter.convert("com.bitresolution.xtest.cli.ClassSourceConverter")

        then:
        assert fqcn.classes == [new FullyQualifiedClassName(ClassSourceConverter)] as Set
    }

    def "should throw exception if error creating FullyQualifiedClassName from argument"() {
        given:
        ClassSourceConverter converter = new ClassSourceConverter()

        when:
        converter.convert(null)

        then:
        thrown(ParameterException)
    }
}
