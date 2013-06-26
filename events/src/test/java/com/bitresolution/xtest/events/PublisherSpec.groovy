package com.bitresolution.xtest.events

import com.bitresolution.succor.junit.category.Unit
import org.junit.experimental.categories.Category
import spock.lang.Shared
import spock.lang.Specification

import static java.util.Collections.emptySet

@Category(Unit)
abstract class PublisherSpec extends Specification {

    @Shared
    Subscriber subscriberA = Mock(Subscriber)
    @Shared
    Subscriber subscriberB = Mock(Subscriber)

    Publisher publisher

    def "should have no subscribers when instantiated"() {
        expect: assert publisher.subscribers == emptySet()
    }

    def "should allow individual subscriptions"() {
        when:
        def subscribed = subscribers.each { publisher.subscribe(it) }
        then:
        assert subscribed.inject { o, i -> o && i }
        assert publisher.subscribers.size() == subscribers.size()
        assert publisher.subscribers == subscribers as Set
        where:
        subscribers << [[subscriberA], [subscriberA, subscriberB]]
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
        assert subscribed.inject { o, i -> o && i }
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
        // can't make abstract due to groovy bug, check when 2.2.1 is out
        throw new UnsupportedOperationException("Must implement in subclass")
    }
}
