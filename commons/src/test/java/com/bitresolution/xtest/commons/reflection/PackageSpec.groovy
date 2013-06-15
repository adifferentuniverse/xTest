package com.bitresolution.xtest.commons.reflection

import spock.lang.Specification

class PackageSpec extends Specification {

    def "package with no arguments is the default package"() {
        given:
        def p = new com.bitresolution.xtest.commons.reflection.Package();
        expect:
        assert p.defaultPackage
        assert p.parts == []
        assert p.parent == p
    }

    def "package with no name is the default package"() {
        given:
        def p = new com.bitresolution.xtest.commons.reflection.Package(blank);
        expect:
        assert p.defaultPackage
        assert p.parts == []
        assert p.parent == p

        where:
        blank << ["", null]
    }

    def "should create Package from parts"() {
        given:
        def p = new com.bitresolution.xtest.commons.reflection.Package(parts as String[]);

        expect:
        assert !p.defaultPackage
        assert p.parts == parts as String[]
        assert p.name == name

        where:
        parts                    | name
        ["com"]                  | "com"
        ["com", "bitresolution"] | "com.bitresolution"
    }

    def "should create Package from string"() {
        given:
        def p = new com.bitresolution.xtest.commons.reflection.Package(name);

        expect:
        assert !p.defaultPackage
        assert p.parts == parts as String[]
        assert p.name == name

        where:
        parts                    | name
        ["com"]                  | "com"
        ["com", "bitresolution"] | "com.bitresolution"
    }

    def "should get child Package"() {
        given:
        def p = new com.bitresolution.xtest.commons.reflection.Package(name);

        when:
        def childPackage = p.getChild(child)

        then:
        assert p != childPackage
        assert childPackage.name == expected

        where:
        name                | child           | expected
        "com"               | "bitresolution" | "com.bitresolution"
        "com.bitresolution" | "rhapsody"      | "com.bitresolution.rhapsody"
    }

    def "should get parent Package"() {
        given:
        def p = new com.bitresolution.xtest.commons.reflection.Package(name);

        when:
        def parent = p.getParent()

        then:
        assert p != parent
        assert parent.name == expected

        where:
        name                | expected
        "com"               | ""
        "com.bitresolution" | "com"
    }

    def "should have meaningful toString"() {
        expect:
        assert new com.bitresolution.xtest.commons.reflection.Package("com.bitresolution").toString().contains("com.bitresolution")
    }
}
