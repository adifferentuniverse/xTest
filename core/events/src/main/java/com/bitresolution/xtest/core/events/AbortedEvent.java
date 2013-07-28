package com.bitresolution.xtest.core.events;

import javax.validation.constraints.NotNull;

public class AbortedEvent extends BaseEvent {
    public AbortedEvent(@NotNull Object source) {
        super(source);
    }

    public static AbortedEvent aborted(@NotNull Object source) {
        return new AbortedEvent(source);
    }
}
