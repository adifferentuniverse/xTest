package com.bitresolution.xtest.phases.compile

import com.bitresolution.succor.junit.category.Unit
import com.bitresolution.xtest.phases.compile.nodes.GenericNode
import com.bitresolution.xtest.phases.compile.nodes.Root
import com.bitresolution.xtest.phases.compile.nodes.XNode
import com.bitresolution.xtest.phases.compile.relationships.ContainsRelationship
import com.bitresolution.xtest.phases.compile.relationships.DependsOnRelationship
import com.bitresolution.xtest.phases.compile.relationships.Relationship
import com.google.common.base.Optional
import edu.uci.ics.jung.graph.Graph
import spock.lang.Specification

@org.junit.experimental.categories.Category(Unit.class)
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
        XNode<Void> node = new GenericNode<Void>(null)

        when:
        graph.addNode(node)

        then:
        assert graph.contains(node)
    }

    def "should throw exception if unable to add a node"() {
        given:
        Graph backingGraph = Mock(Graph)
        TestGraph graph = new JungTestGraph(backingGraph, new GenericNode<Root>(Root.ROOT))
        backingGraph.addVertex(_) >> false

        when:
        graph.addNode(new GenericNode<Void>(null))

        then:
        thrown(CompileGraphException)
    }

    def "should be able to add a relationship between two nodes"() {
        given:
        TestGraph graph = new JungTestGraph()
        XNode<String> nodeA = new GenericNode<String>("A")
        XNode<String> nodeB = new GenericNode<String>("B")
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
        XNode<String> nodeA = new GenericNode<String>("A")
        XNode<String> nodeB = new GenericNode<String>("B")
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
        XNode<String> nodeA = new GenericNode<String>("A")
        XNode<String> nodeB = new GenericNode<String>("B")
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

    def "should not be able to add a relationship between when destination node does not exist in the graph"() {
        given:
        TestGraph graph = new JungTestGraph()
        XNode<String> nodeA = new GenericNode<String>("A")
        XNode<String> nodeB = new GenericNode<String>("B")
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
        XNode<String> nodeA = new GenericNode<String>("A")
        XNode<String> nodeB = new GenericNode<String>("B")
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
        XNode<String> nodeA = new GenericNode<String>("A")
        XNode<String> nodeB = new GenericNode<String>("B")
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
        XNode<String> nodeA = new GenericNode<String>("A")
        XNode<String> nodeB = new GenericNode<String>("B")
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
        XNode<String> nodeA = new GenericNode<String>("A")
        XNode<String> nodeB = new GenericNode<String>("B")
        XNode<String> nodeC = new GenericNode<String>("C")
        XNode<String> nodeD = new GenericNode<String>("D")
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
        XNode<String> nodeA = new GenericNode<String>("A")
        XNode<String> nodeB = new GenericNode<String>("B")
        XNode<String> nodeC = new GenericNode<String>("C")
        XNode<String> nodeD = new GenericNode<String>("D")
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

    def "should return node if found in graph"() {
        given:
        TestGraph graph = new JungTestGraph()
        XNode<String> nodeA = new GenericNode<String>("A")
        XNode<String> nodeB = new GenericNode<String>("B")
        XNode<String> nodeC = new GenericNode<String>("C")
        XNode<String> nodeD = new GenericNode<String>("D")
        Relationship relationshipAB = new ContainsRelationship(nodeA, nodeB)
        Relationship relationshipBC = new ContainsRelationship(nodeB, nodeC)
        Relationship relationshipBD = new ContainsRelationship(nodeB, nodeD)
        Relationship relationshipDC = new DependsOnRelationship(nodeD, nodeC)

        graph.addNode(nodeA)
        graph.addNode(nodeB)
        graph.addNode(nodeC)
        graph.addNode(nodeD)
        graph.addRelationship(relationshipAB)
        graph.addRelationship(relationshipBC)
        graph.addRelationship(relationshipBD)
        graph.addRelationship(relationshipDC)

        when:
        Optional<XNode<String>> result = graph.findNodeByValue("A")

        then:
        result.isPresent()
        result.get() == nodeA
    }

    def "should return none if node not found in graph"() {
        given:
        TestGraph graph = new JungTestGraph()

        when:
        Optional<XNode<String>> result = graph.findNodeByValue("A")

        then:
        !result.present
    }

    def "should honor equals and hashcode contract"() {
        expect:
        left.equals(right) == equal
        left.equals(right) == right.equals(left)
        left.equals(left) == right.equals(right)

        if(right) {
            left.hashCode().equals(right.hashCode()) == equal
        }

        where:
        left                | right               | equal
        new JungTestGraph() | null                | false
        new JungTestGraph() | ""                  | false
        new JungTestGraph() | new JungTestGraph() | true
    }

    def "should have meaningful toString"() {
        expect:
        String string = new JungTestGraph().toString()
        string.contains("relationships")
        string.contains("nodes")
    }
}
