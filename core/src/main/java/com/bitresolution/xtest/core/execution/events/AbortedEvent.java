package com.bitresolution.xtest.core.execution.events;

public class AbortedEvent extends TestExecutorEvent {
    public AbortedEvent(Object source) {
        super(source);
    }

    public static AbortedEvent aborted(Object source) {
        return new AbortedEvent(source);
    }
}
