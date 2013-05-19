package com.bitresolution.xtest.core.graph

import com.bitresolution.xtest.core.graph.nodes.BaseNode
import com.bitresolution.xtest.core.graph.nodes.Root
import com.bitresolution.xtest.core.graph.nodes.Node
import com.bitresolution.xtest.core.graph.relationships.Relationship
import com.bitresolution.xtest.core.graph.relationships.Relationships
import spock.lang.Specification

import java.awt.image.renderable.ContextualRenderedImageFactory


class JungTestGraphSpec extends Specification {

    def "should have a root node"() {
        given:
        TestGraph graph = new JungTestGraph()

        when:
        def root = graph.rootNode

        then:
        assert root.value == Root.ROOT
    }

    def "should be able to add a node"() {
        given:
        TestGraph graph = new JungTestGraph()
        Node<Void> node = new BaseNode<Void>(null, graph)

        when:
        graph.addNode(node)

        then:
        assert graph.contains(node)
    }

    def "should be able to add a relationship between two nodes"() {
        given:
        TestGraph graph = new JungTestGraph()
        Node<String> nodeA = new BaseNode<String>("A", graph)
        Node<String> nodeB = new BaseNode<String>("B", graph)
        Relationship relationship = Relationships.dependsOn()

        graph.addNode(nodeA)
        graph.addNode(nodeB)

        when:
        graph.addRelationship(nodeA, nodeB, relationship)

        then:
        assert graph.contains(nodeA)
        assert graph.contains(nodeB)
        assert graph.contains(relationship)
    }

    def "should be able to remove a relationship between two nodes"() {
        given:
        TestGraph graph = new JungTestGraph()
        Node<String> nodeA = new BaseNode<String>("A", graph)
        Node<String> nodeB = new BaseNode<String>("B", graph)
        Relationship relationship = Relationships.dependsOn()

        graph.addNode(nodeA)
        graph.addNode(nodeB)
        graph.addRelationship(nodeA, nodeB, relationship)

        when:
        graph.removeRelationship(relationship)

        then:
        assert graph.contains(nodeA)
        assert graph.contains(nodeB)
        assert !graph.contains(relationship)
    }

    def "should be able to get inbound relationships of a node"() {
        given:
        TestGraph graph = new JungTestGraph()
        Node<String> nodeA = new BaseNode<String>("A", graph)
        Node<String> nodeB = new BaseNode<String>("B", graph)
        Relationship relationship = Relationships.dependsOn()

        when:
        graph.addNode(nodeA)
        graph.addNode(nodeB)
        graph.addRelationship(nodeA, nodeB, relationship)

        then:
        assert graph.getInboundRelationships(nodeA) == [] as Set
        assert graph.getInboundRelationships(nodeB) == [relationship] as Set
    }

    def "should be able to get outbound relationships of a node"() {
        given:
        TestGraph graph = new JungTestGraph()
        Node<String> nodeA = new BaseNode<String>("A", graph)
        Node<String> nodeB = new BaseNode<String>("B", graph)
        Relationship relationship = Relationships.dependsOn()

        when:
        graph.addNode(nodeA)
        graph.addNode(nodeB)
        graph.addRelationship(nodeA, nodeB, relationship)

        then:
        assert graph.getOutboundRelationships(nodeA) == [relationship] as Set
        assert graph.getOutboundRelationships(nodeB) == [] as Set
    }

    def "should be able to get nodes adjacent by an outbound relationship of a node"() {
        given:
        TestGraph graph = new JungTestGraph()
        Node<String> nodeA = new BaseNode<String>("A", graph)
        Node<String> nodeB = new BaseNode<String>("B", graph)
        Node<String> nodeC = new BaseNode<String>("C", graph)
        Node<String> nodeD = new BaseNode<String>("D", graph)
        Relationship relationshipAB = Relationships.dependsOn()
        Relationship relationshipBC = Relationships.dependsOn()
        Relationship relationshipBD = Relationships.dependsOn()

        when:
        graph.addNode(nodeA)
        graph.addNode(nodeB)
        graph.addNode(nodeC)
        graph.addNode(nodeD)
        graph.addRelationship(nodeA, nodeB, relationshipAB)
        graph.addRelationship(nodeB, nodeC, relationshipBC)
        graph.addRelationship(nodeB, nodeD, relationshipBD)

        then:
        assert graph.getAdjacentNodes(nodeA) == [nodeB] as Set
        assert graph.getAdjacentNodes(nodeB) == [nodeC, nodeD] as Set
        assert graph.getAdjacentNodes(nodeC) == [] as Set
        assert graph.getAdjacentNodes(nodeD) == [] as Set
    }

    def "should be able to get nodes adjacent by type of an outbound relationship of a node"() {
        given:
        TestGraph graph = new JungTestGraph()
        Node<String> nodeA = new BaseNode<String>("A", graph)
        Node<String> nodeB = new BaseNode<String>("B", graph)
        Node<String> nodeC = new BaseNode<String>("C", graph)
        Node<String> nodeD = new BaseNode<String>("D", graph)
        Relationship relationshipAB = Relationships.contains()
        Relationship relationshipBC = Relationships.contains()
        Relationship relationshipBD = Relationships.contains()
        Relationship relationshipDC = Relationships.dependsOn()

        when:
        graph.addNode(nodeA)
        graph.addNode(nodeB)
        graph.addNode(nodeC)
        graph.addNode(nodeD)
        graph.addRelationship(nodeA, nodeB, relationshipAB)
        graph.addRelationship(nodeB, nodeC, relationshipBC)
        graph.addRelationship(nodeB, nodeD, relationshipBD)
        graph.addRelationship(nodeD, nodeC, relationshipDC)

        then:
        assert graph.getAdjacentNodesByRelationship(nodeA, Relationships.ContainsRelationship) == [nodeB] as Set
        assert graph.getAdjacentNodesByRelationship(nodeB, Relationships.ContainsRelationship) == [nodeC, nodeD] as Set
        assert graph.getAdjacentNodesByRelationship(nodeC, Relationships.ContainsRelationship) == [] as Set
        assert graph.getAdjacentNodesByRelationship(nodeD, Relationships.ContainsRelationship) == [] as Set
    }
}
