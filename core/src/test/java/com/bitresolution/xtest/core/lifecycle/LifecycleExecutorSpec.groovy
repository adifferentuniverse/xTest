package com.bitresolution.xtest.core.lifecycle

import com.bitresolution.xtest.core.lifecycle.phase.BasePhase
import com.bitresolution.xtest.events.Publisher
import spock.lang.Specification

import static com.bitresolution.xtest.core.events.AbortedEvent.aborted
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

    def "should propogate exceptions fron lifecycle phasess"() {
        given:
        Publisher publisher = Mock(Publisher)
        LifecycleExecutor executor = new LifecycleExecutor(publisher)
        Lifecycle lifecycle = new Lifecycle()
        MockPhase<Void, Integer> phaseA = new MockPhase(Void, Integer, 1)
        Phase<Integer, Integer> phaseB = new MockExceptionThrowingPhase(Integer, Integer)
        MockPhase<Integer, Void> phaseC = new MockPhase(Integer, Void, null)

        lifecycle.add(phaseA)
                .add(phaseB)
                .add(phaseC)

        when:
        executor.execute(lifecycle)

        then:
        1 * publisher.publish(start(executor))
        assert phaseA.executed
        thrown(LifecycleExecutorException)
        assert !phaseC.executed
        1 * publisher.publish(aborted(executor))
    }
}

class MockPhase<I, O> extends BasePhase<I, O> {

    O value
    boolean executed

    MockPhase(Class<I> inputType, Class<O> onputType, O value) {
        super(inputType, onputType)
        this.value = value
        this.executed = false
    }

    @Override
    O execute(I inpur) throws LifecycleExecutorException {
        executed = true
        return value
    }
}

class MockExceptionThrowingPhase<I, O> extends BasePhase<I, O> {

    MockExceptionThrowingPhase(Class<I> inputType, Class<O> onputType) {
        super(inputType, onputType)
    }

    @Override
    O execute(I inpur) throws LifecycleExecutorException {
        throw new LifecycleExecutorException("Error")
    }
}
