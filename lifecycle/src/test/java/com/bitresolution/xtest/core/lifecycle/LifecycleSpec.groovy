package com.bitresolution.xtest.core.lifecycle

import com.bitresolution.succor.junit.category.Unit
import com.google.common.collect.Iterators
import com.google.common.collect.Lists
import spock.lang.Specification

@org.junit.experimental.categories.Category(Unit.class)
class LifecycleSpec extends Specification {

    def "should have no phases on instantiation"() {
        when:
        def lifecycle = new Lifecycle()

        then:
        assert lifecycle.length == 0
        assert Iterators.size(lifecycle.iterator()) == 0
        assert lifecycle.tailType == Void
    }

    def "should allow phases with void input type as starting phase"() {
        given:
        def lifecycle = new Lifecycle()
        MockPhase<Void, Integer> phase = new MockPhase<Void, Integer>(Void, Integer, 1)

        when:
        lifecycle.add(phase)

        then:
        assert lifecycle.length == 1
        assert Lists.newArrayList(lifecycle.iterator()) == [phase]
        assert lifecycle.tailType == Integer
    }

    def "should only allow phases with void input type as starting phase"() {
        given:
        def lifecycle = new Lifecycle()
        MockPhase<Integer, Integer> phase = new MockPhase<Integer, Integer>(Integer, Integer, 1)

        when:
        lifecycle.add(phase)

        then:
        thrown(LifecycleExecutorException)
        assert lifecycle.length == 0
    }

    def "should allow a chain of phases"() {
        given:
        def lifecycle = new Lifecycle()
        MockPhase<Void, Integer> phaseA = new MockPhase<Void, Integer>(Void, Integer, 1)
        MockPhase<Integer, Integer> phaseB = new MockPhase<Integer, Integer>(Integer, Integer, 2)
        MockPhase<Integer, String> phaseC = new MockPhase<Integer, String>(Integer, String, "2")

        when:
        lifecycle.add(phaseA)
        lifecycle.add(phaseB)
        lifecycle.add(phaseC)

        then:
        assert lifecycle.length == 3
        assert Lists.newArrayList(lifecycle.iterator()) == [phaseA, phaseB, phaseC]
        assert lifecycle.tailType == String
    }

    def "should not allow phase with input type not matching tail type to be added"() {
        given:
        def lifecycle = new Lifecycle()
        MockPhase<Void, Integer> phaseA = new MockPhase<Void, Integer>(Void, Integer, 1)
        MockPhase<Integer, Integer> phaseB = new MockPhase<Integer, Integer>(Integer, Integer, 2)
        MockPhase<String, String> phaseC = new MockPhase<String, String>(String, String, "2")

        when:
        lifecycle.add(phaseA)
        lifecycle.add(phaseB)
        lifecycle.add(phaseC)

        then:
        thrown(LifecycleExecutorException)
        assert lifecycle.length == 2
        assert Lists.newArrayList(lifecycle.iterator()) == [phaseA, phaseB]
        assert lifecycle.tailType == Integer
    }
}
