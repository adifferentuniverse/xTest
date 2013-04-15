package com.bitresolution.xtest.events;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class BlockingPublisher<L extends Subscriber> implements Publisher<L> {

    private final Set<L> subscribers;

    public BlockingPublisher(){
        this.subscribers = new CopyOnWriteArraySet<L>();
    }

    @Override
    public boolean subscribe(L subscriber) {
        return subscribers.add(subscriber);
    }

    @Override
    public boolean subscribe(Collection<L> subscribers) {
        return subscribers.addAll(subscribers);
    }

    @Override
    public boolean unsubscribe(L subscriber) {
        return subscribers.remove(subscriber);
    }

    @Override
    public boolean unsubscribe(Collection<L> subscribers) {
        return subscribers.removeAll(subscribers);
    }

    @Override
    public void publish(XEvent event) {
        for(L subscriber : subscribers) {
            subscriber.process(event);
        }
    }
}
