package com.bitresolution.xtest.core.events;

public class AbortedEvent extends BaseEvent {
    public AbortedEvent(Object source) {
        super(source);
    }

    public static AbortedEvent aborted(Object source) {
        return new AbortedEvent(source);
    }
}
