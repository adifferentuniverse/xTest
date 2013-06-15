package com.bitresolution.xtest.core.events;

import com.bitresolution.xtest.core.phases.execute.Fixture;

public class QueuedEvent extends BaseEvent {
    public QueuedEvent(Fixture source) {
        super(source);
    }

    public static QueuedEvent queued(Fixture source) {
        return new QueuedEvent(source);
    }
}
