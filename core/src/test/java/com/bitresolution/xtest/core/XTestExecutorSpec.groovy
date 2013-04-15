package com.bitresolution.xtest.core

import com.bitresolution.xtest.core.execution.GraphTestExecutor
import com.bitresolution.xtest.core.execution.TestExectionListener
import com.bitresolution.xtest.events.BlockingPublisher
import com.bitresolution.xtest.events.Publisher
import spock.lang.Specification

import static com.bitresolution.xtest.core.execution.events.CompleteEvent.complete
import static com.bitresolution.xtest.core.execution.events.StartEvent.start

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
}
