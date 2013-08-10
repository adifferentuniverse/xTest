package com.bitresolution.xtest.phases.generate;

import com.bitresolution.xtest.core.lifecycle.LifecycleExecutorException;
import com.bitresolution.xtest.core.lifecycle.Phase;
import com.bitresolution.xtest.events.Publisher;
import com.bitresolution.xtest.phases.compile.TestGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

import static com.bitresolution.xtest.core.events.CompleteEvent.complete;
import static com.bitresolution.xtest.core.events.StartEvent.start;

@Component
public class CompileFixturesPhase implements Phase<TestGraph, Fixtures> {

    private static final Logger log = LoggerFactory.getLogger(CompileFixturesPhase.class);

    private final Publisher publisher;

    @Autowired
    public CompileFixturesPhase(Publisher publisher) {
        this.publisher = publisher;
    }

    @NotNull
    @Override
    public Class<TestGraph> getInputType() {
        return TestGraph.class;
    }

    @NotNull
    @Override
    public Class<Fixtures> getOutputType() {
        return Fixtures.class;
    }

    @NotNull
    @Override
    public Fixtures execute(@NotNull TestGraph input) throws LifecycleExecutorException {
        publisher.publish(start(this));
        Fixtures fixtures = new Fixtures();
        publisher.publish(complete(this));
        return fixtures;
    }

    @NotNull
    @Override
    public String getName() {
        return "generate-fixtures";
    }

    @Override
    public String toString() {
        return getName();
    }
}
