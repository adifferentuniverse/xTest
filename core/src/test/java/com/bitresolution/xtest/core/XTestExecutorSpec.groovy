package com.bitresolution.xtest.core

import com.bitresolution.xtest.core.execution.GraphTestExecutor
import com.bitresolution.xtest.core.execution.TestExectionListener
import com.bitresolution.xtest.core.graph.TestGraph
import com.bitresolution.xtest.core.graph.XTestAnnotationBasedGraphFactory
import com.bitresolution.xtest.events.BlockingPublisher
import com.bitresolution.xtest.events.Publisher
import com.bitresolution.xtest.examples.TestNodeEmptyClassExample
import org.junit.experimental.categories.Category
import spock.lang.Specification

import static com.bitresolution.TestCategories.Unit
import static com.bitresolution.xtest.core.execution.events.CompleteEvent.complete
import static com.bitresolution.xtest.core.execution.events.StartEvent.start

@Category(Unit.class)
class XTestExecutorSpec extends Specification {

    def "should execute empty graph successfully"() {
        given:
        TestGraph graph = Mock()
        TestExectionListener listener = Mock()
        Publisher<TestExectionListener> publisher = new BlockingPublisher<TestExectionListener>()
        publisher.subscribe(listener)

        def testExecutor = new GraphTestExecutor(publisher)

        when:
        testExecutor.execute(graph)

        then:
        1 * listener.process(start(testExecutor))
        1 * listener.process(complete(testExecutor))
    }

    def "should generate single vertex graph from TestNode on class wuth no nested TestNodes"() {
        given:
        TestGraph graph = new XTestAnnotationBasedGraphFactory().from(TestNodeEmptyClassExample)
        TestExectionListener listener = Mock()
        Publisher<TestExectionListener> publisher = new BlockingPublisher<TestExectionListener>()
        publisher.subscribe(listener)

        def testExecutor = new GraphTestExecutor(publisher)

        when:
        testExecutor.execute(graph)

        then:
        1 * listener.process(start(testExecutor))
        1 * listener.process(complete(testExecutor))
    }
}
