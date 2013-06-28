package com.bitresolution.xtest.core.phases.generate;

import com.bitresolution.xtest.core.lifecycle.LifecycleExecutorException;
import com.bitresolution.xtest.core.lifecycle.Phase;
import com.bitresolution.xtest.core.phases.compile.TestGraph;
import com.bitresolution.xtest.core.phases.execute.Fixtures;
import com.bitresolution.xtest.events.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenerateFixturesPhase implements Phase<TestGraph, Fixtures> {

    private static final Logger log = LoggerFactory.getLogger(GenerateFixturesPhase.class);

    private final Publisher publisher;

    @Autowired
    public GenerateFixturesPhase(Publisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public Class<TestGraph> getInputType() {
        return TestGraph.class;
    }

    @Override
    public Class<Fixtures> getOutputType() {
        return Fixtures.class;
    }

    @Override
    public Fixtures execute(TestGraph input) throws LifecycleExecutorException {
        log.debug("Executing phase: {}", getName());
        return new Fixtures();
    }

    @Override
    public String getName() {
        return "generate-fixtures";
    }

    @Override
    public String toString() {
        return getName();
    }
}
