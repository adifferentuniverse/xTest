package com.bitresolution.xtest.events;

import javax.validation.constraints.NotNull;

public class StartEvent extends BaseEvent {
    public StartEvent(@NotNull Object source) {
        super(source);
    }

    public static StartEvent start(@NotNull Object source) {
        return new StartEvent(source);
    }
}
