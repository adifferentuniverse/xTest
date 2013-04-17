package com.bitresolution.xtest.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class NonBlockingPublisher<L extends Subscriber> implements Publisher<L> {

    private static final Logger log = LoggerFactory.getLogger(NonBlockingPublisher.class);

    private final Set<L> subscribers;

    public NonBlockingPublisher(){
        this.subscribers = new CopyOnWriteArraySet<L>();
    }

    @Override
    public boolean subscribe(L subscriber) {
        return subscribers.add(subscriber);
    }

    @Override
    public boolean subscribe(Collection<L> subscribers) {
        return this.subscribers.addAll(subscribers);
    }

    @Override
    public boolean unsubscribe(L subscriber) {
        return this.subscribers.remove(subscriber);
    }

    @Override
    public boolean unsubscribe(Collection<L> subscribers) {
        return this.subscribers.removeAll(subscribers);
    }

    @Override
    public Set<L> getSubscribers() {
        return Collections.unmodifiableSet(subscribers);
    }

    @Override
    public void publish(XEvent event) {
        log.debug("Publishing {} to subscribers", new Object[]{event});
        for(L subscriber : subscribers) {
            subscriber.process(event);
        }
        log.debug("Published {} to {} subscribers", new Object[]{event, subscribers.size()});
    }
}
