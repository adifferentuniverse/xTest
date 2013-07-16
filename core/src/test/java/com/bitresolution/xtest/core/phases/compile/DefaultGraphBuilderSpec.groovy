package com.bitresolution.xtest.core.phases.compile

import com.bitresolution.succor.reflection.FullyQualifiedClassName
import com.bitresolution.succor.reflection.PackageName
import com.bitresolution.xtest.core.phases.compile.nodes.ClassNode
import com.bitresolution.xtest.core.phases.compile.nodes.MethodNode
import com.bitresolution.xtest.core.phases.compile.relationships.ContainsRelationship
import com.bitresolution.xtest.examples.TestNodeClassWithNoTestNodeMethodsExample
import com.bitresolution.xtest.examples.TestNodeEmptyClassExample
import com.bitresolution.xtest.examples.TestNodeMultipleMethodExample
import com.bitresolution.xtest.examples.TestNodeSingleMethodExample
import spock.lang.Specification

class DefaultGraphBuilderSpec extends Specification {

    def "should create empty test graph from non-annotated class"() {
        given:
        def factory = new DefaultGraphBuilder()
        def klass = String.class

        when:
        factory.from(klass)

        then:
        thrown(TestGraphException)
    }

    def "should create test graph from class with TestNode annotation but no methods"() {
        given:
        def factory = new DefaultGraphBuilder()
        def klass = TestNodeEmptyClassExample.class

        when:
        def graph = factory.from(klass)

        then:
        def nodes = graph.getAdjacentNodesByRelationship(graph.rootNode, ContainsRelationship.class).toList()
        assert nodes.size() == 1
        assert nodes[0] == new ClassNode(new FullyQualifiedClassName(TestNodeEmptyClassExample))
        assert graph.getAdjacentNodes(nodes[0]).size() == 0
    }

    def "should ignore non-annotated methods when creating test graph from class with TestNode annotation"() {
        given:
        def factory = new DefaultGraphBuilder()
        def klass = TestNodeClassWithNoTestNodeMethodsExample.class

        when:
        def graph = factory.from(klass)

        then:
        def nodes = graph.getAdjacentNodesByRelationship(graph.rootNode, ContainsRelationship.class).toList()
        assert nodes.size() == 1
        assert nodes[0] == new ClassNode(new FullyQualifiedClassName(TestNodeClassWithNoTestNodeMethodsExample))
        assert graph.getAdjacentNodes(nodes[0]).size() == 0
    }

    def "should create test graph from class with TestNode annotation and a TestNode annotated method"() {
        given:
        def factory = new DefaultGraphBuilder()
        def klass = TestNodeSingleMethodExample.class

        when:
        def graph = factory.from(klass)

        then:
        def nodes = graph.getAdjacentNodesByRelationship(graph.rootNode, ContainsRelationship.class).toList()
        assert nodes.size() == 1
        assert nodes[0] == new ClassNode(new FullyQualifiedClassName(TestNodeSingleMethodExample))

        def methodNodes = graph.getAdjacentNodes(nodes[0]).toList()
        assert methodNodes.size() == 1
        assert methodNodes[0] == new MethodNode(TestNodeSingleMethodExample.class.getMethod("shouldTestSomething"))
    }

    def "should create test graph from class with TestNode annotation and annotated methods"() {
        given:
        def factory = new DefaultGraphBuilder()
        def klass = TestNodeMultipleMethodExample.class

        when:
        def graph = factory.from(klass)

        then:
        def nodes = graph.getAdjacentNodesByRelationship(graph.rootNode, ContainsRelationship.class).toList()
        assert nodes.size() == 1
        assert nodes[0] == new ClassNode(new FullyQualifiedClassName(TestNodeMultipleMethodExample))

        def methodNodes = graph.getAdjacentNodes(nodes[0]).toList()
        assert methodNodes.size() == 3
        assert methodNodes.containsAll([
                new MethodNode(TestNodeMultipleMethodExample.class.getMethod("shouldTestA")),
                new MethodNode(TestNodeMultipleMethodExample.class.getMethod("shouldTestB")),
                new MethodNode(TestNodeMultipleMethodExample.class.getMethod("shouldTestC")),
        ])
    }

    def "should create test graph from fully qualified class name"() {
        given:
        def factory = new DefaultGraphBuilder()
        def klass = new FullyQualifiedClassName("com.bitresolution.xtest.examples.TestNodeSingleMethodExample")

        when:
        def graph = factory.from(klass)

        then:
        def nodes = graph.getAdjacentNodesByRelationship(graph.rootNode, ContainsRelationship.class).toList()
        assert nodes.size() == 1
        assert nodes[0] == new ClassNode(new FullyQualifiedClassName(TestNodeSingleMethodExample))

        def methodNodes = graph.getAdjacentNodes(nodes[0]).toList()
        assert methodNodes.size() == 1
        assert methodNodes[0] == new MethodNode(TestNodeSingleMethodExample.class.getMethod("shouldTestSomething"))
    }

    def "should create empty test graph from package with no annotated members"() {
        given:
        def factory = new DefaultGraphBuilder()

        when:
        def graph = factory.from(new PackageName("com.bitresolution.xtest.core"))

        then:
        def nodes = graph.getAdjacentNodesByRelationship(graph.rootNode, ContainsRelationship.class).toList()
        assert nodes.size() == 0
    }

    def "should create test graph from package with annotated members"() {
        given:
        def factory = new DefaultGraphBuilder()

        when:
        def graph = factory.from(new PackageName("com.bitresolution.xtest.examples"))

        then:
        def nodes = graph.getAdjacentNodesByRelationship(graph.rootNode, ContainsRelationship.class).toList()
        assert nodes.size() == 5
        assert nodes.containsAll(
                new ClassNode(new FullyQualifiedClassName(TestNodeEmptyClassExample)),
                new ClassNode(new FullyQualifiedClassName(TestNodeClassWithNoTestNodeMethodsExample)),
                new ClassNode(new FullyQualifiedClassName(TestNodeSingleMethodExample)),
                new ClassNode(new FullyQualifiedClassName(TestNodeMultipleMethodExample))
        )
    }
}
