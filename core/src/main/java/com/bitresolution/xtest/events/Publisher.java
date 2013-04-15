package com.bitresolution.xtest.events;

import com.bitresolution.xtest.core.XTestEvent;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class Publisher<L extends Subscriber> {

    private final Set<L> subscribers;

    public Publisher(){
        this.subscribers = new CopyOnWriteArraySet<L>();
    }

    public boolean subscribe(L subscriber) {
        return subscribers.add(subscriber);
    }

    public boolean subscribe(Collection<L> subscribers) {
        return subscribers.addAll(subscribers);
    }

    public boolean unsubscribe(L subscriber) {
        return subscribers.remove(subscriber);
    }

    public boolean unsubscribe(Collection<L> subscribers) {
        return subscribers.removeAll(subscribers);
    }

    public void publish(XTestEvent event) {
        for(L subscriber : subscribers) {
            subscriber.process(event);
        }
    }
}
