package com.bitresolution.xtest.core.events;

public class CompleteEvent extends BaseEvent {
    public CompleteEvent(Object source) {
        super(source);
    }

    public static CompleteEvent complete(Object source) {
        return new CompleteEvent(source);
    }
}
