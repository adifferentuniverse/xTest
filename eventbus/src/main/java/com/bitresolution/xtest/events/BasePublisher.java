package com.bitresolution.xtest.events;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public abstract class BasePublisher implements Publisher {
    protected final Set<Subscriber> subscribers;

    public BasePublisher() {
        this(new CopyOnWriteArraySet<Subscriber>());
    }

    protected BasePublisher(@NotNull Set<Subscriber> subscribers) {
        this.subscribers = subscribers;
    }

    public boolean subscribe(@NotNull Subscriber subscriber) {
        return subscribers.add(subscriber);
    }

    public boolean subscribe(@NotNull Collection<Subscriber> subscribers) {
        return this.subscribers.addAll(subscribers);
    }

    public boolean unsubscribe(@NotNull Subscriber subscriber) {
        return this.subscribers.remove(subscriber);
    }

    public boolean unsubscribe(@NotNull Collection<Subscriber> subscribers) {
        return this.subscribers.removeAll(subscribers);
    }

    @NotNull
    public Set<Subscriber> getSubscribers() {
        return Collections.unmodifiableSet(subscribers);
    }

    public abstract void publish(XEvent event);
}
