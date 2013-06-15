package com.bitresolution.xtest.core.lifecycle;

import com.bitresolution.xtest.events.Publisher;

import static com.bitresolution.xtest.core.events.AbortedEvent.aborted;
import static com.bitresolution.xtest.core.events.CompleteEvent.complete;
import static com.bitresolution.xtest.core.events.StartEvent.start;

public class LifecycleExecutor {

    private final Publisher publisher;

    public LifecycleExecutor(Publisher publisher) {
        this.publisher = publisher;
    }

    @SuppressWarnings("unchecked")
    public void execute(Lifecycle lifecycle) throws LifecycleExecutorException {
        publisher.publish(start(this));
        try {
            Object result = null;
            for(Phase phase : lifecycle) {
                result = phase.execute(phase.getInputType().cast(result));
            }
        }
        catch (LifecycleExecutorException e) {
            publisher.publish(aborted(this));
            throw e;
        }
        publisher.publish(complete(this));
    }
}
