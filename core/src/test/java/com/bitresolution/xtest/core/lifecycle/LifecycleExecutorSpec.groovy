package com.bitresolution.xtest.core.lifecycle

import com.bitresolution.xtest.core.lifecycle.phase.BasePhase
import com.bitresolution.xtest.events.Publisher
import spock.lang.Specification

import static com.bitresolution.xtest.core.events.CompleteEvent.complete
import static com.bitresolution.xtest.core.events.StartEvent.start

class LifecycleExecutorSpec extends Specification {

    def "should publish lifecycle execution start and finish events"() {
        given:
        Publisher publisher = Mock(Publisher)
        LifecycleExecutor executor = new LifecycleExecutor(publisher)
        Lifecycle lifecycle = new Lifecycle()

        when:
        executor.execute(lifecycle)

        then:
        1 * publisher.publish(start(executor))
        1 * publisher.publish(complete(executor))
    }

    def "should execute lifecycle phases in order"() {
        given:
        Publisher publisher = Mock(Publisher)
        LifecycleExecutor executor = new LifecycleExecutor(publisher)
        Lifecycle lifecycle = new Lifecycle()
        MockPhase<Void, Integer> phaseA = new MockPhase(Void, Integer, 1)
        MockPhase<Integer, Integer> phaseB = new MockPhase(Integer, Integer, 2)
        MockPhase<Integer, Void> phaseC = new MockPhase(Integer, Void, null)

        lifecycle.add(phaseA)
                .add(phaseB)
                .add(phaseC)

        when:
        executor.execute(lifecycle)

        then:
        1 * publisher.publish(start(executor))
        assert phaseA.executed
        assert phaseB.executed
        assert phaseC.executed
        1 * publisher.publish(complete(executor))
    }
}

class MockPhase<I, O> extends BasePhase<I, O> {

    O value
    boolean executed

    protected MockPhase(Class<I> inputType, Class<O> onputType, O value) {
        super(inputType, onputType)
        this.value = value
        this.executed = false
    }

    @Override
    O execute(I inpur) {
        executed = true
        return value
    }
}
