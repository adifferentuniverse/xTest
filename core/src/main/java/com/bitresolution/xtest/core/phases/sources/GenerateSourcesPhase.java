package com.bitresolution.xtest.core.phases.sources;

import com.bitresolution.xtest.core.lifecycle.LifecycleExecutorException;
import com.bitresolution.xtest.core.lifecycle.Phase;
import com.bitresolution.xtest.events.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenerateSourcesPhase implements Phase<Void, Sources> {

    private static final Logger log = LoggerFactory.getLogger(GenerateSourcesPhase.class);

    private final Publisher publisher;

    @Autowired
    public GenerateSourcesPhase(Publisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public Class<Void> getInputType() {
        return Void.class;
    }

    @Override
    public Class<Sources> getOutputType() {
        return Sources.class;
    }

    @Override
    public Sources execute(Void input) throws LifecycleExecutorException {
        log.debug("Executing phase: {}", getName());
        return new Sources();
    }

    @Override
    public String getName() {
        return "generate-sources";
    }

    @Override
    public String toString() {
        return getName();
    }
}
