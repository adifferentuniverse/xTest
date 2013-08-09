package com.bitresolution.xtest.core.phases.compile;

import com.bitresolution.xtest.core.lifecycle.LifecycleExecutorException;
import com.bitresolution.xtest.core.lifecycle.Phase;
import com.bitresolution.xtest.core.phases.sources.Sources;
import com.bitresolution.xtest.events.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

import static com.bitresolution.xtest.core.events.CompleteEvent.complete;
import static com.bitresolution.xtest.core.events.StartEvent.start;

@Component
public class CompileGraphPhase implements Phase<Sources, TestGraph> {

    private static final Logger log = LoggerFactory.getLogger(CompileGraphPhase.class);

    @NotNull
    private final Publisher publisher;
    @NotNull
    private final GraphBuilder builder;

    @Autowired
    public CompileGraphPhase(@NotNull Publisher publisher, @NotNull GraphBuilder builder) {
        this.publisher = publisher;
        this.builder = builder;
    }

    @Override
    @NotNull
    public Class<Sources> getInputType() {
        return Sources.class;
    }

    @Override
    @NotNull
    public Class<TestGraph> getOutputType() {
        return TestGraph.class;
    }

    @Override
    @NotNull
    public TestGraph execute(@NotNull Sources input) throws LifecycleExecutorException {
        log.debug("Executing phase: {}", getName());
        publisher.publish(start(this));
        TestGraph graph;
        try {
            graph = builder.add(input).build();
        }
        catch (Exception e) {
            throw new LifecycleExecutorException("Error executing phase: {}", getName(), e);
        }
        publisher.publish(complete(this));
        return graph;
    }

    @Override
    @NotNull
    public String getName() {
        return "compile-graph";
    }
}
