package com.bitresolution.xtest.core.execution.events;

import com.bitresolution.xtest.core.execution.Fixture;

public class QueuedEvent extends TestExecutorEvent {
    public QueuedEvent(Fixture source) {
        super(source);
    }

    public static QueuedEvent queued(Fixture source) {
        return new QueuedEvent(source);
    }
}
