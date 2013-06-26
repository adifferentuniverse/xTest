package com.bitresolution.xtest.events

import com.bitresolution.succor.junit.category.Unit
import org.junit.experimental.categories.Category

@Category(Unit)
class BlockingPublisherSpec extends PublisherSpec {

    def setup() {
        publisher = new BlockingPublisher()
    }

    @Override
    def "should publish events to all subscribera"() {
        given:
        Subscriber a = Mock(Subscriber)
        Subscriber b = Mock(Subscriber)
        publisher.subscribe([a, b])
        XEvent event = Mock(XEvent)
        when:
        publisher.publish(event)
        then:
        1 * a.process(event)
        1 * b.process(event)
    }
}
