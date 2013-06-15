package com.bitresolution.xtest.core.lifecycle

import com.bitresolution.xtest.events.Publisher
import spock.lang.Specification

import static com.bitresolution.xtest.core.events.CompleteEvent.complete
import static com.bitresolution.xtest.core.events.StartEvent.start

class LifecycleExecutorSpec extends Specification {

    def "should publish lifecycle execution start and finish events"() {
        given:
        Publisher publisher = Mock(Publisher)
        LifecycleExecutor executor = new LifecycleExecutor(publisher)
        Lifecycle lifecycle = new Lifecycle ()

        when:
        executor.execute(lifecycle)

        then:
        1 * publisher.publish(start(executor))
        1 * publisher.publish(complete(executor))
    }
}
