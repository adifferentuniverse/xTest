package com.bitresolution.xtest.core.phases.compile;

import com.bitresolution.xtest.core.lifecycle.LifecycleExecutorException;
import com.bitresolution.xtest.core.lifecycle.Phase;
import com.bitresolution.xtest.core.phases.sources.Sources;
import com.bitresolution.xtest.events.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompileGraphPhase implements Phase<Sources, TestGraph> {

    private static final Logger log = LoggerFactory.getLogger(CompileGraphPhase.class);

    private final Publisher publisher;

    @Autowired
    public CompileGraphPhase(Publisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public Class<Sources> getInputType() {
        return Sources.class;
    }

    @Override
    public Class<TestGraph> getOutputType() {
        return TestGraph.class;
    }

    @Override
    public TestGraph execute(Sources input) throws LifecycleExecutorException {
        log.debug("Executing phase: {}", getName());
        return new JungTestGraph();
    }

    @Override
    public String getName() {
        return "compile-graph";
    }

    @Override
    public String toString() {
        return getName();
    }
}
