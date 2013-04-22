package com.bitresolution.xtest.core.execution;

import com.bitresolution.xtest.core.graph.TestGraph;
import com.bitresolution.xtest.events.Publisher;
import com.bitresolution.xtest.events.XEventSource;

import static com.bitresolution.xtest.core.execution.events.CompleteEvent.complete;
import static com.bitresolution.xtest.core.execution.events.StartEvent.start;

public class GraphTestExecutor implements TestExecutor, XEventSource {

    private Publisher<TestExectionListener> publisher;

    public GraphTestExecutor(Publisher<TestExectionListener> publisher) {
        this.publisher = publisher;
    }

    public void execute(TestGraph p) {
        publisher.publish(start(this));
        publisher.publish(complete(this));
    }

    @Override
    public Publisher<TestExectionListener> getPublisher() {
        return publisher;
    }
}
