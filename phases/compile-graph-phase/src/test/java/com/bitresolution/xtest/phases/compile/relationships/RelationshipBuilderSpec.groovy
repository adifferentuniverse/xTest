package com.bitresolution.xtest.phases.compile.relationships

import com.bitresolution.succor.junit.category.Unit
import com.bitresolution.xtest.phases.compile.nodes.GenericNode
import com.bitresolution.xtest.phases.compile.nodes.XNode
import com.bitresolution.xtest.phases.compile.relationships.ContainsRelationship
import com.bitresolution.xtest.phases.compile.relationships.DependsOnRelationship
import com.bitresolution.xtest.phases.compile.relationships.Relationship
import spock.lang.Specification

import static com.bitresolution.xtest.phases.compile.relationships.RelationshipBuilder.where


@org.junit.experimental.categories.Category(Unit.class)
class RelationshipBuilderSpec extends Specification {

    def "should build contains relationships"() {
        given:
        XNode<String> nodeA = new GenericNode<String>("A")
        XNode<String> nodeB = new GenericNode<String>("B")

        when:
        Relationship relationship = where(nodeA).contains(nodeB)

        then:
        relationship instanceof ContainsRelationship
        relationship.source == nodeA
        relationship.destination == nodeB
    }

    def "should build depends on relationships"() {
        given:
        XNode<String> nodeA = new GenericNode<String>("A")
        XNode<String> nodeB = new GenericNode<String>("B")

        when:
        Relationship relationship = where(nodeA).dependsOn(nodeB)

        then:
        relationship instanceof DependsOnRelationship
        relationship.source == nodeA
        relationship.destination == nodeB
    }
}
