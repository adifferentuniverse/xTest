package com.bitresolution.xtest.core.execution.events;

public class CompleteEvent extends TestExecutorEvent {
    public CompleteEvent(Object source) {
        super(source);
    }

    public static CompleteEvent complete(Object source) {
        return new CompleteEvent(source);
    }
}
