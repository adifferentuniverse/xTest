package com.bitresolution.xtest.events;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public abstract class BasePublisher implements Publisher {
    protected final Set<Subscriber> subscribers;

    public BasePublisher() {
        this(new CopyOnWriteArraySet<Subscriber>());
    }

    protected BasePublisher(Set<Subscriber> subscribers) {
        this.subscribers = subscribers;
    }

    public boolean subscribe(Subscriber subscriber) {
        return subscribers.add(subscriber);
    }

    public boolean subscribe(Collection<Subscriber> subscribers) {
        return this.subscribers.addAll(subscribers);
    }

    public boolean unsubscribe(Subscriber subscriber) {
        return this.subscribers.remove(subscriber);
    }

    public boolean unsubscribe(Collection<Subscriber> subscribers) {
        return this.subscribers.removeAll(subscribers);
    }

    public Set<Subscriber> getSubscribers() {
        return Collections.unmodifiableSet(subscribers);
    }

    public abstract void publish(XEvent event);
}
