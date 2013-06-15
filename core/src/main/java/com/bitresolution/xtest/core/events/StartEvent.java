package com.bitresolution.xtest.core.events;

public class StartEvent extends BaseEvent {
    public StartEvent(Object source) {
        super(source);
    }

    public static StartEvent start(Object source) {
        return new StartEvent(source);
    }
}
