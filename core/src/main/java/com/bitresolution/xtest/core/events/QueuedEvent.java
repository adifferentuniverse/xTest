package com.bitresolution.xtest.core.events;

import com.bitresolution.xtest.core.phases.execute.Fixture;

import javax.validation.constraints.NotNull;

public class QueuedEvent extends BaseEvent {
    public QueuedEvent(@NotNull Fixture source) {
        super(source);
    }

    public static QueuedEvent queued(@NotNull Fixture source) {
        return new QueuedEvent(source);
    }
}
