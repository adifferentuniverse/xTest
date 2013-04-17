package com.bitresolution.xtest.events

import static com.bitresolution.TestCategories.Unit
import spock.lang.Shared
import spock.lang.Specification
import org.junit.experimental.categories.Category

import static java.util.Collections.emptySet


@Category(Unit)
class NonBlockingPublisherSpec extends Specification {

    @Shared
    Subscriber subscriberA = Mock()
    @Shared
    Subscriber subscriberB = Mock()

    Publisher<Subscriber> publisher = new BlockingPublisher<Subscriber>()

    def "should have no subscribers when instantiated"() {
        expect: assert publisher.subscribers == emptySet()
    }

    def "should allow individual subscriptions"() {
        when:
            def subscribed = subscribers.each { publisher.subscribe(it) }
        then:
            assert subscribed.inject {o, i -> o && i}
            assert publisher.subscribers.size() == subscribers.size()
            assert publisher.subscribers == subscribers as Set
        where:
            subscribers << [[subscriberA],  [subscriberA, subscriberB]]
    }

    def "should allow bulk subscriptions"() {
        given:
            List<Subscriber> subscribers = [subscriberA, subscriberB]
        when:
            boolean subscribed = publisher.subscribe(subscribers)
        then:
            assert subscribed
            assert publisher.subscribers.size() == subscribers.size()
            assert publisher.subscribers == subscribers as Set
    }

    def "should allow individual unsubscriptions"() {
        given:
            publisher.subscribe([subscriberA, subscriberB])
        when:
            def subscribed = removed.each { publisher.unsubscribe(it) }
        then:
            assert subscribed.inject {o, i -> o && i}
            assert publisher.subscribers.size() == remaining.size()
            assert publisher.subscribers == remaining as Set
        where:
            removed                    | remaining
            [subscriberA]              | [subscriberB]
            [subscriberA, subscriberB] | []
    }

    def "should allow bulk unsubscriptions"() {
        given:
            List<Subscriber> subscribers = [subscriberA, subscriberB]
            publisher.subscribe(subscribers)
        when:
            boolean subscribed = publisher.unsubscribe(subscribers)
        then:
            assert subscribed
            assert publisher.subscribers.empty
    }

    def "should publish events to all subscribera"() {
        given:
            Subscriber a = Mock()
            Subscriber b = Mock()
            publisher.subscribe([a, b])
            XEvent event = Mock(XEvent)
        when:
            publisher.publish(event)
        then:
            1 * a.process(event)
            1 * b.process(event)
    }
}
