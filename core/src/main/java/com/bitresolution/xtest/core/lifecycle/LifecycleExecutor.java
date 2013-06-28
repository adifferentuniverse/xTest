package com.bitresolution.xtest.core.lifecycle;

import com.bitresolution.xtest.events.Publisher;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import static com.bitresolution.xtest.core.events.AbortedEvent.aborted;
import static com.bitresolution.xtest.core.events.CompleteEvent.complete;
import static com.bitresolution.xtest.core.events.StartEvent.start;

@Component
public class LifecycleExecutor {

    private final Publisher publisher;
    private final Lifecycle lifecycle;

    @Inject
    public LifecycleExecutor(Lifecycle lifecycle, Publisher publisher) {
        this.lifecycle = lifecycle;
        this.publisher = publisher;
    }

    @SuppressWarnings("unchecked")
    public void execute() throws LifecycleExecutorException {
        publisher.publish(start(this));
        try {
            Object result = null;
            for(Phase phase : lifecycle) {
                publisher.publish(start(phase));
                result = phase.execute(phase.getInputType().cast(result));
                publisher.publish(complete(phase));
            }
        }
        catch (LifecycleExecutorException e) {
            publisher.publish(aborted(this));
            throw e;
        }
        publisher.publish(complete(this));
    }
}
