package com.bitresolution.xtest.core.phases.compile

import com.bitresolution.xtest.core.phases.compile.nodes.BaseNode
import com.bitresolution.xtest.core.phases.compile.nodes.Root
import com.bitresolution.xtest.core.phases.compile.nodes.XNode
import com.bitresolution.xtest.core.phases.compile.relationships.ContainsRelationship
import com.bitresolution.xtest.core.phases.compile.relationships.DependsOnRelationship
import com.bitresolution.xtest.core.phases.compile.relationships.Relationship
import spock.lang.Specification

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
        XNode<Void> node = new BaseNode<Void>(null)

        when:
        graph.addNode(node)

        then:
        assert graph.contains(node)
    }

    def "should be able to add a relationship between two nodes"() {
        given:
        TestGraph graph = new JungTestGraph()
        XNode<String> nodeA = new BaseNode<String>("A")
        XNode<String> nodeB = new BaseNode<String>("B")
        Relationship relationship = new DependsOnRelationship(nodeA, nodeB)

        graph.addNode(nodeA)
        graph.addNode(nodeB)

        when:
        graph.addRelationship(relationship)

        then:
        assert graph.contains(nodeA)
        assert graph.contains(nodeB)
        assert graph.contains(new DependsOnRelationship(nodeA, nodeB))
    }

    def "should not be able to add a relationship between when source node does not exist in the graph"() {
        given:
        TestGraph graph = new JungTestGraph()
        XNode<String> nodeA = new BaseNode<String>("A")
        XNode<String> nodeB = new BaseNode<String>("B")
        Relationship relationship = new DependsOnRelationship(nodeA, nodeB)

        graph.addNode(nodeB)

        when:
        graph.addRelationship(relationship)

        then:
        thrown CompileGraphException
    }

    def "should not be able to add a duplicate relationship between nodes"() {
        given:
        TestGraph graph = new JungTestGraph()
        XNode<String> nodeA = new BaseNode<String>("A")
        XNode<String> nodeB = new BaseNode<String>("B")
        Relationship relationship = new DependsOnRelationship(nodeA, nodeB)
        Relationship relationshipCopy = new DependsOnRelationship(nodeA, nodeB)

        graph.addNode(nodeA)
        graph.addNode(nodeB)
        graph.addRelationship(relationship)

        when:
        graph.addRelationship(relationshipCopy)

        then:
        thrown CompileGraphException
    }

    def "should not be able to add a relationship between when detination node does not exist in the graph"() {
        given:
        TestGraph graph = new JungTestGraph()
        XNode<String> nodeA = new BaseNode<String>("A")
        XNode<String> nodeB = new BaseNode<String>("B")
        Relationship relationship = new DependsOnRelationship(nodeA, nodeB)

        graph.addNode(nodeA)

        when:
        graph.addRelationship(relationship)

        then:
        thrown CompileGraphException
    }

    def "should be able to remove a relationship between two nodes"() {
        given:
        TestGraph graph = new JungTestGraph()
        XNode<String> nodeA = new BaseNode<String>("A")
        XNode<String> nodeB = new BaseNode<String>("B")
        Relationship relationship = new DependsOnRelationship(nodeA, nodeB)

        graph.addNode(nodeA)
        graph.addNode(nodeB)
        graph.addRelationship(relationship)

        when:
        graph.removeRelationship(relationship)

        then:
        assert graph.contains(nodeA)
        assert graph.contains(nodeB)
        assert !graph.contains(new DependsOnRelationship(nodeA, nodeB))
    }

    def "should be able to get inbound relationships of a node"() {
        given:
        TestGraph graph = new JungTestGraph()
        XNode<String> nodeA = new BaseNode<String>("A")
        XNode<String> nodeB = new BaseNode<String>("B")
        Relationship relationship = new DependsOnRelationship(nodeA, nodeB)

        when:
        graph.addNode(nodeA)
        graph.addNode(nodeB)
        graph.addRelationship(relationship)

        then:
        assert graph.getInboundRelationships(nodeA) == [] as Set
        assert graph.getInboundRelationships(nodeB) == [new DependsOnRelationship(nodeA, nodeB)] as Set
    }

    def "should be able to get outbound relationships of a node"() {
        given:
        TestGraph graph = new JungTestGraph()
        XNode<String> nodeA = new BaseNode<String>("A")
        XNode<String> nodeB = new BaseNode<String>("B")
        Relationship relationship = new DependsOnRelationship(nodeA, nodeB)

        when:
        graph.addNode(nodeA)
        graph.addNode(nodeB)
        graph.addRelationship(relationship)

        then:
        assert graph.getOutboundRelationships(nodeA) == [new DependsOnRelationship(nodeA, nodeB)] as Set
        assert graph.getOutboundRelationships(nodeB) == [] as Set
    }

    def "should be able to get nodes adjacent by an outbound relationship of a node"() {
        given:
        TestGraph graph = new JungTestGraph()
        XNode<String> nodeA = new BaseNode<String>("A")
        XNode<String> nodeB = new BaseNode<String>("B")
        XNode<String> nodeC = new BaseNode<String>("C")
        XNode<String> nodeD = new BaseNode<String>("D")
        Relationship relationshipAB = new DependsOnRelationship(nodeA, nodeB)
        Relationship relationshipBC = new DependsOnRelationship(nodeB, nodeC)
        Relationship relationshipBD = new DependsOnRelationship(nodeB, nodeD)

        when:
        graph.addNode(nodeA)
        graph.addNode(nodeB)
        graph.addNode(nodeC)
        graph.addNode(nodeD)
        graph.addRelationship(relationshipAB)
        graph.addRelationship(relationshipBC)
        graph.addRelationship(relationshipBD)

        then:
        assert graph.getAdjacentNodes(nodeA) == [nodeB] as Set
        assert graph.getAdjacentNodes(nodeB) == [nodeC, nodeD] as Set
        assert graph.getAdjacentNodes(nodeC) == [] as Set
        assert graph.getAdjacentNodes(nodeD) == [] as Set
    }

    def "should be able to get nodes adjacent by type of an outbound relationship of a node"() {
        given:
        TestGraph graph = new JungTestGraph()
        XNode<String> nodeA = new BaseNode<String>("A")
        XNode<String> nodeB = new BaseNode<String>("B")
        XNode<String> nodeC = new BaseNode<String>("C")
        XNode<String> nodeD = new BaseNode<String>("D")
        Relationship relationshipAB = new ContainsRelationship(nodeA, nodeB)
        Relationship relationshipBC = new ContainsRelationship(nodeB, nodeC)
        Relationship relationshipBD = new ContainsRelationship(nodeB, nodeD)
        Relationship relationshipDC = new DependsOnRelationship(nodeD, nodeC)

        when:
        graph.addNode(nodeA)
        graph.addNode(nodeB)
        graph.addNode(nodeC)
        graph.addNode(nodeD)
        graph.addRelationship(relationshipAB)
        graph.addRelationship(relationshipBC)
        graph.addRelationship(relationshipBD)
        graph.addRelationship(relationshipDC)

        then:
        assert graph.getAdjacentNodesByRelationship(nodeA, ContainsRelationship) == [nodeB] as Set
        assert graph.getAdjacentNodesByRelationship(nodeB, ContainsRelationship) == [nodeC, nodeD] as Set
        assert graph.getAdjacentNodesByRelationship(nodeC, ContainsRelationship) == [] as Set
        assert graph.getAdjacentNodesByRelationship(nodeD, ContainsRelationship) == [] as Set
    }


    def "should honor equals contract"() {
        expect:
        left.equals(right) == equal
        left.equals(right) == right.equals(left)
        left.equals(left) == right.equals(right)

        where:
        left                | right               | equal
        new JungTestGraph() | null                | false
        new JungTestGraph() | ""                  | false
        new JungTestGraph() | new JungTestGraph() | true

    }

}
