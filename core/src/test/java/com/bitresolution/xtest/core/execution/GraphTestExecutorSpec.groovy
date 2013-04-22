package com.bitresolution.xtest.core.execution

import com.bitresolution.xtest.core.graph.TestGraph
import com.bitresolution.xtest.events.Publisher
import spock.lang.Specification

import static com.bitresolution.xtest.core.execution.events.CompleteEvent.complete
import static com.bitresolution.xtest.core.execution.events.StartEvent.start

class GraphTestExecutorSpec extends Specification {

    def "should be xEvent source"() {
        given:
        TestGraph graph = Mock()
        Publisher<TestExectionListener> publisher = Mock()
        TestExectionListener listener = Mock()
        GraphTestExecutor testExecutor = new GraphTestExecutor(publisher)

        when:
        testExecutor.publisher.subscribe(listener)

        then:
        1 * publisher.subscribe(listener)
    }

    def "should signal start and end of execution"() {
        given:
        TestGraph graph = Mock()
        Publisher<TestExectionListener> publisher = Mock()
        GraphTestExecutor testExecutor = new GraphTestExecutor(publisher)

        when:
        testExecutor.execute(graph)

        then:
        1 * publisher.publish(start(testExecutor))
        1 * publisher.publish(complete(testExecutor))
    }
}
