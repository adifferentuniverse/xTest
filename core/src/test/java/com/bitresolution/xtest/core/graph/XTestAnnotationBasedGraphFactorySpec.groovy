package com.bitresolution.xtest.core.graph

import com.bitresolution.xtest.core.graph.nodes.ClassNode
import com.bitresolution.xtest.core.graph.nodes.MethodNode
import com.bitresolution.xtest.core.graph.relationships.Relationships
import com.bitresolution.xtest.examples.TestNodeClassWithNoTestNodeMethodsExample
import com.bitresolution.xtest.examples.TestNodeEmptyClassExample
import com.bitresolution.xtest.examples.TestNodeMultipleMethodExample
import com.bitresolution.xtest.examples.TestNodeSingleMethodExample
import com.bitresolution.xtest.reflection.FullyQualifiedClassName
import com.bitresolution.xtest.reflection.Package
import spock.lang.Specification

class XTestAnnotationBasedGraphFactorySpec extends Specification {

    def "should create empty test graph from non-annotated class"() {
        given:
        def factory = new XTestAnnotationBasedGraphFactory()
        def klass = String.class

        when:
        factory.from(klass)

        then:
        thrown(TestGraphException)
    }

    def "should create test graph from class with TestNode annotation but no methods"() {
        given:
        def factory = new XTestAnnotationBasedGraphFactory()
        def klass = TestNodeEmptyClassExample.class

        when:
        def graph = factory.from(klass)

        then:
        def nodes = graph.getRootNode().getAdjacentNodesByRelationship(Relationships.contains()).toList()
        assert nodes.size() == 1
        assert nodes[0] == new ClassNode(TestNodeEmptyClassExample, graph)
        assert nodes[0].getAdjacentNodes().size() == 0
    }

    def "should ignore non-annotated methods when creating test graph from class with TestNode annotation"() {
        given:
        def factory = new XTestAnnotationBasedGraphFactory()
        def klass = TestNodeClassWithNoTestNodeMethodsExample.class

        when:
        def graph = factory.from(klass)

        then:
        def nodes = graph.getRootNode().getAdjacentNodesByRelationship(Relationships.contains()).toList()
        assert nodes.size() == 1
        assert nodes[0] == new ClassNode(TestNodeClassWithNoTestNodeMethodsExample, graph)
        assert nodes[0].getAdjacentNodes().size() == 0
    }

    def "should create test graph from class with TestNode annotation and a TestNode annotated method"() {
        given:
        def factory = new XTestAnnotationBasedGraphFactory()
        def klass = TestNodeSingleMethodExample.class

        when:
        def graph = factory.from(klass)

        then:
        def nodes = graph.getRootNode().getAdjacentNodesByRelationship(Relationships.contains()).toList()
        assert nodes.size() == 1
        assert nodes[0] == new ClassNode(TestNodeSingleMethodExample, graph)

        def methodNodes = nodes[0].getAdjacentNodes().toList()
        assert methodNodes.size() == 1
        assert methodNodes[0] == new MethodNode(TestNodeSingleMethodExample.class.getMethod("shouldTestSomething"), graph)
    }

    def "should create test graph from class with TestNode annotation and annotated methods"() {
        given:
        def factory = new XTestAnnotationBasedGraphFactory()
        def klass = TestNodeMultipleMethodExample.class

        when:
        def graph = factory.from(klass)

        then:
        def nodes = graph.getRootNode().getAdjacentNodesByRelationship(Relationships.contains()).toList()
        assert nodes.size() == 1
        assert nodes[0] == new ClassNode(TestNodeMultipleMethodExample, graph)

        def methodNodes = nodes[0].getAdjacentNodes().toList()
        assert methodNodes.size() == 3
        assert methodNodes.containsAll([
                new MethodNode(TestNodeMultipleMethodExample.class.getMethod("shouldTestA"), graph),
                new MethodNode(TestNodeMultipleMethodExample.class.getMethod("shouldTestB"), graph),
                new MethodNode(TestNodeMultipleMethodExample.class.getMethod("shouldTestC"), graph),
        ])
    }

    def "should create test graph from fully qualified class name"() {
        given:
        def factory = new XTestAnnotationBasedGraphFactory()
        def klass = new FullyQualifiedClassName("com.bitresolution.xtest.examples.TestNodeSingleMethodExample")

        when:
        def graph = factory.from(klass)

        then:
        def nodes = graph.getRootNode().getAdjacentNodesByRelationship(Relationships.contains()).toList()
        assert nodes.size() == 1
        assert nodes[0] == new ClassNode(TestNodeSingleMethodExample, graph)

        def methodNodes = nodes[0].getAdjacentNodes().toList()
        assert methodNodes.size() == 1
        assert methodNodes[0] == new MethodNode(TestNodeSingleMethodExample.class.getMethod("shouldTestSomething"), graph)
    }

    def "should create empty test graph from package with no annotated members"() {
        given:
        def factory = new XTestAnnotationBasedGraphFactory()

        when:
        def graph = factory.from(new Package("com.bitresolution.xtest.core"))

        then:
        def nodes = graph.getRootNode().getAdjacentNodesByRelationship(Relationships.contains()).toList()
        assert nodes.size() == 0
    }

    def "should create test graph from package with annotated members"() {
        given:
        def factory = new XTestAnnotationBasedGraphFactory()

        when:
        def graph = factory.from(new Package("com.bitresolution.xtest.examples"))

        then:
        def nodes = graph.getRootNode().getAdjacentNodesByRelationship(Relationships.contains()).toList()
        assert nodes.size() == 4
        assert nodes.containsAll(
                new ClassNode(TestNodeEmptyClassExample, graph),
                new ClassNode(TestNodeClassWithNoTestNodeMethodsExample, graph),
                new ClassNode(TestNodeSingleMethodExample, graph),
                new ClassNode(TestNodeMultipleMethodExample, graph)
        )
    }
}
