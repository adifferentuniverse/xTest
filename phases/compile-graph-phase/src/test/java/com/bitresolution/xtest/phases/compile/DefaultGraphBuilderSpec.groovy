package com.bitresolution.xtest.phases.compile

import com.bitresolution.succor.junit.category.Unit
import com.bitresolution.succor.reflection.FullyQualifiedClassName
import com.bitresolution.succor.reflection.FullyQualifiedMethodName
import com.bitresolution.succor.reflection.PackageName
import com.bitresolution.xtest.phases.compile.DefaultGraphBuilder
import com.bitresolution.xtest.phases.compile.GraphBuilder
import com.bitresolution.xtest.phases.compile.JungTestGraph
import com.bitresolution.xtest.phases.compile.TestGraph
import com.bitresolution.xtest.phases.compile.nodes.ClassNode
import com.bitresolution.xtest.phases.compile.nodes.MethodNode
import com.bitresolution.xtest.phases.compile.nodes.PackageNode
import com.bitresolution.xtest.phases.sources.Sources
import com.bitresolution.xtest.examples.TestNodeClassWithNoTestNodeMethodsExample
import com.bitresolution.xtest.examples.TestNodeEmptyClassExample
import com.bitresolution.xtest.examples.TestNodeMultipleMethodExample
import com.bitresolution.xtest.examples.TestNodeSingleMethodExample
import spock.lang.Specification

import static com.bitresolution.xtest.phases.compile.relationships.RelationshipBuilder.where

@org.junit.experimental.categories.Category(Unit.class)
class DefaultGraphBuilderSpec extends Specification {

    GraphBuilder builder = new DefaultGraphBuilder()

    def "should create empty test graph from non-annotated class"() {
        given:
        builder.add(new Sources([new FullyQualifiedClassName(String)] as Set))

        when:
        TestGraph graph = builder.build()

        then:
        graph == new JungTestGraph()
    }

    def "should create test graph from class with TestNode annotation but no methods"() {
        given:
        FullyQualifiedClassName className = new FullyQualifiedClassName(TestNodeEmptyClassExample)
        builder.add(new Sources([className] as Set))

        when:
        TestGraph graph = builder.build()

        then:
        PackageNode com = new PackageNode(new PackageName("com"))
        PackageNode comBitresolution = new PackageNode(new PackageName("com.bitresolution"))
        PackageNode comBitresolutionXtest = new PackageNode(new PackageName("com.bitresolution.xtest"))
        PackageNode comBitresolutionXtestExamples = new PackageNode(new PackageName("com.bitresolution.xtest.examples"))
        ClassNode testClass = new ClassNode(className)

        def expected = new JungTestGraph()
        expected.addNode(com)
        expected.addNode(comBitresolution)
        expected.addNode(comBitresolutionXtest)
        expected.addNode(comBitresolutionXtestExamples)
        expected.addRelationship(where(expected.rootNode).contains(com))
        expected.addRelationship(where(com).contains(comBitresolution))
        expected.addRelationship(where(comBitresolution).contains(comBitresolutionXtest))
        expected.addRelationship(where(comBitresolutionXtest).contains(comBitresolutionXtestExamples))

        expected.addNode(testClass)
        expected.addRelationship(where(comBitresolutionXtestExamples).contains(testClass))

        assert expected.equals(graph)
    }

    def "should ignore non-annotated methods when creating test graph from class with TestNode annotation"() {
        given:
        builder.add(new Sources([new FullyQualifiedClassName(TestNodeClassWithNoTestNodeMethodsExample)] as Set))

        when:
        TestGraph graph = builder.build()

        then:
        PackageNode com = new PackageNode(new PackageName("com"))
        PackageNode comBitresolution = new PackageNode(new PackageName("com.bitresolution"))
        PackageNode comBitresolutionXtest = new PackageNode(new PackageName("com.bitresolution.xtest"))
        PackageNode comBitresolutionXtestExamples = new PackageNode(new PackageName("com.bitresolution.xtest.examples"))
        ClassNode testClass = new ClassNode(new FullyQualifiedClassName(TestNodeClassWithNoTestNodeMethodsExample))

        def expected = new JungTestGraph()
        expected.addNode(com)
        expected.addNode(comBitresolution)
        expected.addNode(comBitresolutionXtest)
        expected.addNode(comBitresolutionXtestExamples)
        expected.addRelationship(where(expected.rootNode).contains(com))
        expected.addRelationship(where(com).contains(comBitresolution))
        expected.addRelationship(where(comBitresolution).contains(comBitresolutionXtest))
        expected.addRelationship(where(comBitresolutionXtest).contains(comBitresolutionXtestExamples))

        expected.addNode(testClass)
        expected.addRelationship(where(comBitresolutionXtestExamples).contains(testClass))

        assert expected.equals(graph)
    }

    def "should create test graph from class with TestNode annotation and a TestNode annotated method"() {
        given:
        builder.add(new Sources([new FullyQualifiedClassName(TestNodeSingleMethodExample)] as Set))

        when:
        TestGraph graph = builder.build()

        then:
        PackageNode com = new PackageNode(new PackageName("com"))
        PackageNode comBitresolution = new PackageNode(new PackageName("com.bitresolution"))
        PackageNode comBitresolutionXtest = new PackageNode(new PackageName("com.bitresolution.xtest"))
        PackageNode comBitresolutionXtestExamples = new PackageNode(new PackageName("com.bitresolution.xtest.examples"))
        ClassNode testClass = new ClassNode(new FullyQualifiedClassName(TestNodeSingleMethodExample))
        MethodNode method = new MethodNode(new FullyQualifiedMethodName(TestNodeSingleMethodExample.class.getMethod("shouldTestSomething")))

        def expected = new JungTestGraph()
        expected.addNode(com)
        expected.addNode(comBitresolution)
        expected.addNode(comBitresolutionXtest)
        expected.addNode(comBitresolutionXtestExamples)
        expected.addRelationship(where(expected.rootNode).contains(com))
        expected.addRelationship(where(com).contains(comBitresolution))
        expected.addRelationship(where(comBitresolution).contains(comBitresolutionXtest))
        expected.addRelationship(where(comBitresolutionXtest).contains(comBitresolutionXtestExamples))

        expected.addNode(testClass)
        expected.addRelationship(where(comBitresolutionXtestExamples).contains(testClass))

        expected.addNode(method)
        expected.addRelationship(where(testClass).contains(method))

        assert expected.equals(graph)
    }

    def "should create test graph from class with TestNode annotation and annotated methods"() {
        given:
        builder.add(new Sources([new FullyQualifiedClassName(TestNodeMultipleMethodExample)] as Set))

        when:
        TestGraph graph = builder.build()

        then:
        PackageNode com = new PackageNode(new PackageName("com"))
        PackageNode comBitresolution = new PackageNode(new PackageName("com.bitresolution"))
        PackageNode comBitresolutionXtest = new PackageNode(new PackageName("com.bitresolution.xtest"))
        PackageNode comBitresolutionXtestExamples = new PackageNode(new PackageName("com.bitresolution.xtest.examples"))
        ClassNode testClass = new ClassNode(new FullyQualifiedClassName(TestNodeMultipleMethodExample))
        MethodNode methodA = new MethodNode(new FullyQualifiedMethodName(TestNodeMultipleMethodExample.class.getMethod("shouldTestA")))
        MethodNode methodB = new MethodNode(new FullyQualifiedMethodName(TestNodeMultipleMethodExample.class.getMethod("shouldTestB")))
        MethodNode methodC = new MethodNode(new FullyQualifiedMethodName(TestNodeMultipleMethodExample.class.getMethod("shouldTestC")))

        def expected = new JungTestGraph()
        expected.addNode(com)
        expected.addNode(comBitresolution)
        expected.addNode(comBitresolutionXtest)
        expected.addNode(comBitresolutionXtestExamples)
        expected.addRelationship(where(expected.rootNode).contains(com))
        expected.addRelationship(where(com).contains(comBitresolution))
        expected.addRelationship(where(comBitresolution).contains(comBitresolutionXtest))
        expected.addRelationship(where(comBitresolutionXtest).contains(comBitresolutionXtestExamples))

        expected.addNode(testClass)
        expected.addRelationship(where(comBitresolutionXtestExamples).contains(testClass))

        expected.addNode(methodA)
        expected.addRelationship(where(testClass).contains(methodA))
        expected.addNode(methodB)
        expected.addRelationship(where(testClass).contains(methodB))
        expected.addNode(methodC)
        expected.addRelationship(where(testClass).contains(methodC))

        assert expected.equals(graph)
    }
}
