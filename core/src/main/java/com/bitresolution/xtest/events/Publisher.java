package com.bitresolution.xtest.events;

import java.util.Collection;
import java.util.Set;

public interface Publisher<L extends Subscriber> {
    boolean subscribe(L subscriber);

    boolean subscribe(Collection<L> subscribers);

    boolean unsubscribe(L subscriber);

    boolean unsubscribe(Collection<L> subscribers);

    void publish(XEvent event);

    Set<L> getSubscribers();
}
