package com.bitresolution.xtest.core.events;

import com.bitresolution.xtest.core.execution.Fixture;

public class QueuedEvent extends BaseEvent {
    public QueuedEvent(Fixture source) {
        super(source);
    }

    public static QueuedEvent queued(Fixture source) {
        return new QueuedEvent(source);
    }
}
