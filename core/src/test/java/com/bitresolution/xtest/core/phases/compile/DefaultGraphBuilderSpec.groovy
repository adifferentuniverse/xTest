package com.bitresolution.xtest.core.phases.compile

import com.bitresolution.succor.reflection.FullyQualifiedClassName
import com.bitresolution.xtest.core.phases.compile.nodes.ClassNode
import com.bitresolution.xtest.core.phases.compile.nodes.MethodNode
import com.bitresolution.xtest.core.phases.compile.relationships.ContainsRelationship
import com.bitresolution.xtest.core.phases.sources.Sources
import com.bitresolution.xtest.examples.TestNodeClassWithNoTestNodeMethodsExample
import com.bitresolution.xtest.examples.TestNodeEmptyClassExample
import com.bitresolution.xtest.examples.TestNodeMultipleMethodExample
import com.bitresolution.xtest.examples.TestNodeSingleMethodExample
import spock.lang.Specification

class DefaultGraphBuilderSpec extends Specification {

    GraphBuilder builder = new DefaultGraphBuilder()

    def "should create empty test graph from non-annotated class"() {
        given:
        builder.add(new Sources([new FullyQualifiedClassName(String)] as Set))

        when:
        TestGraph graph = builder.build()

        then:
        graph == new JungTestGraph()
        graph.getAdjacentNodesByRelationship(graph.rootNode, ContainsRelationship.class).toList().empty
    }

    def "should create test graph from class with TestNode annotation but no methods"() {
        given:
        builder.add(new Sources([new FullyQualifiedClassName(TestNodeEmptyClassExample)] as Set))

        when:
        TestGraph graph = builder.build()

        then:
        def nodes = graph.getAdjacentNodesByRelationship(graph.rootNode, ContainsRelationship.class).toList()
        nodes.size() == 1
        nodes[0] == new ClassNode(new FullyQualifiedClassName(TestNodeEmptyClassExample))
        graph.getAdjacentNodes(nodes[0]).size() == 0
    }

    def "should ignore non-annotated methods when creating test graph from class with TestNode annotation"() {
        given:
        builder.add(new Sources([new FullyQualifiedClassName(TestNodeClassWithNoTestNodeMethodsExample)] as Set))

        when:
        TestGraph graph = builder.build()

        then:
        def nodes = graph.getAdjacentNodesByRelationship(graph.rootNode, ContainsRelationship.class).toList()
        nodes.size() == 1
        nodes[0] == new ClassNode(new FullyQualifiedClassName(TestNodeClassWithNoTestNodeMethodsExample))
        graph.getAdjacentNodes(nodes[0]).size() == 0
    }

    def "should create test graph from class with TestNode annotation and a TestNode annotated method"() {
        given:
        builder.add(new Sources([new FullyQualifiedClassName(TestNodeSingleMethodExample)] as Set))

        when:
        TestGraph graph = builder.build()

        then:
        def nodes = graph.getAdjacentNodesByRelationship(graph.rootNode, ContainsRelationship.class).toList()
        nodes.size() == 1
        nodes[0] == new ClassNode(new FullyQualifiedClassName(TestNodeSingleMethodExample))

        def methodNodes = graph.getAdjacentNodes(nodes[0]).toList()
        methodNodes.size() == 1
        methodNodes[0] == new MethodNode(TestNodeSingleMethodExample.class.getMethod("shouldTestSomething"))
    }

    def "should create test graph from class with TestNode annotation and annotated methods"() {
        given:
        builder.add(new Sources([new FullyQualifiedClassName(TestNodeMultipleMethodExample)] as Set))

        when:
        TestGraph graph = builder.build()

        then:
        def nodes = graph.getAdjacentNodesByRelationship(graph.rootNode, ContainsRelationship.class).toList()
        nodes.size() == 1
        nodes[0] == new ClassNode(new FullyQualifiedClassName(TestNodeMultipleMethodExample))

        def methodNodes = graph.getAdjacentNodes(nodes[0]).toList()
        methodNodes.size() == 3
        methodNodes.containsAll([
                new MethodNode(TestNodeMultipleMethodExample.class.getMethod("shouldTestA")),
                new MethodNode(TestNodeMultipleMethodExample.class.getMethod("shouldTestB")),
                new MethodNode(TestNodeMultipleMethodExample.class.getMethod("shouldTestC")),
        ])
    }

    def "should create test graph from fully qualified class name"() {
        given:
        builder.add(new Sources([new FullyQualifiedClassName("com.bitresolution.xtest.examples.TestNodeSingleMethodExample")] as Set))

        when:
        TestGraph graph = builder.build()

        then:
        def nodes = graph.getAdjacentNodesByRelationship(graph.rootNode, ContainsRelationship.class).toList()
        nodes.size() == 1
        nodes[0] == new ClassNode(new FullyQualifiedClassName(TestNodeSingleMethodExample))

        def methodNodes = graph.getAdjacentNodes(nodes[0]).toList()
        methodNodes.size() == 1
        methodNodes[0] == new MethodNode(TestNodeSingleMethodExample.class.getMethod("shouldTestSomething"))
    }
}
