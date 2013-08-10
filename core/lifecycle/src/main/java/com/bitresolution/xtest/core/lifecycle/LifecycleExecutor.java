package com.bitresolution.xtest.core.lifecycle;

import com.bitresolution.xtest.eventbus.Publisher;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import static com.bitresolution.xtest.events.AbortedEvent.aborted;
import static com.bitresolution.xtest.events.CompleteEvent.complete;
import static com.bitresolution.xtest.events.StartEvent.start;

@Component
public class LifecycleExecutor {

    private final Publisher publisher;
    private final Lifecycle lifecycle;

    @Inject
    public LifecycleExecutor(@NotNull Lifecycle lifecycle, @NotNull Publisher publisher) {
        this.lifecycle = lifecycle;
        this.publisher = publisher;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
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
