package com.bitresolution.xtest.core.phases.sources;

import com.bitresolution.xtest.core.XTestConfiguration;
import com.bitresolution.xtest.core.lifecycle.LifecycleExecutorException;
import com.bitresolution.xtest.core.lifecycle.Phase;
import com.bitresolution.xtest.events.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

import static com.bitresolution.xtest.core.events.CompleteEvent.complete;
import static com.bitresolution.xtest.core.events.StartEvent.start;

@Component
public class GenerateSourcesPhase implements Phase<Void, Sources> {

    private static final Logger log = LoggerFactory.getLogger(GenerateSourcesPhase.class);

    private final Publisher publisher;
    private final SourceBuilder builder;
    private final SourceConfiguration configuration;

    @Autowired
    public GenerateSourcesPhase(Publisher publisher, SourceBuilder builder, XTestConfiguration configuration) {
        this.publisher = publisher;
        this.builder = builder;
        this.configuration = configuration.getSourceConfiguration();
    }

    @NotNull
    @Override
    public Class<Void> getInputType() {
        return Void.class;
    }

    @NotNull
    @Override
    public Class<Sources> getOutputType() {
        return Sources.class;
    }

    @NotNull
    @Override
    public Sources execute(@NotNull Void input) throws LifecycleExecutorException {
        log.debug("Executing phase: {}", getName());
        publisher.publish(start(this));
        Sources sources = builder
                .include(configuration.getIncludedClasses())
                .exclude(configuration.getExcludedClasses())
                .include(configuration.getIncludedPackages())
                .exclude(configuration.getExcludedPackages())
                .build();
        publisher.publish(complete(this));
        return sources;
    }

    @NotNull
    @Override
    public String getName() {
        return "generate-sources";
    }
}
