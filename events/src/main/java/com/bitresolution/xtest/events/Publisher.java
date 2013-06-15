package com.bitresolution.xtest.events;

import java.util.Collection;
import java.util.Set;

public interface Publisher {
    boolean subscribe(Subscriber subscriber);

    boolean subscribe(Collection<Subscriber> subscribers);

    boolean unsubscribe(Subscriber subscriber);

    boolean unsubscribe(Collection<Subscriber> subscribers);

    void publish(XEvent event);

    Set<Subscriber> getSubscribers();
}
