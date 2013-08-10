package com.bitresolution.xtest.events;

import javax.validation.constraints.NotNull;

public class CompleteEvent extends BaseEvent {
    public CompleteEvent(@NotNull Object source) {
        super(source);
    }

    public static CompleteEvent complete(@NotNull Object source) {
        return new CompleteEvent(source);
    }
}
