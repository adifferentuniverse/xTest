package com.bitresolution.xtest.events;

import javax.validation.constraints.NotNull;

public class QueuedEvent extends BaseEvent {
    public QueuedEvent(@NotNull Object source) {
        super(source);
    }

    public static QueuedEvent queued(@NotNull Object source) {
        return new QueuedEvent(source);
    }
}
