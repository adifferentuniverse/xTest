package com.bitresolution.xtest.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlockingPublisher extends BasePublisher {

    private static final Logger log = LoggerFactory.getLogger(BlockingPublisher.class);

    @Override
    public void publish(XEvent event) {
        log.debug("Publishing {} to subscribers", new Object[]{event});
        for(Subscriber subscriber : subscribers) {
            subscriber.process(event);
        }
        log.debug("Published {} to {} subscribers", new Object[]{event, subscribers.size()});
    }
}
