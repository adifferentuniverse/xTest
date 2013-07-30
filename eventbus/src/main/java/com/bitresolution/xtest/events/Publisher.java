package com.bitresolution.xtest.events;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Set;

public interface Publisher {
    boolean subscribe(@NotNull Subscriber subscriber);

    boolean subscribe(@NotNull Collection<Subscriber> subscribers);

    boolean unsubscribe(@NotNull Subscriber subscriber);

    boolean unsubscribe(@NotNull Collection<Subscriber> subscribers);

    void publish(@NotNull XEvent event);

    @NotNull
    Set<Subscriber> getSubscribers();
}
