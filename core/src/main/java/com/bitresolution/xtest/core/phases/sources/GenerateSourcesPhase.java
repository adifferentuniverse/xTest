package com.bitresolution.xtest.core.phases.sources;

import com.bitresolution.xtest.core.SourceConfiguration;
import com.bitresolution.xtest.core.lifecycle.LifecycleExecutorException;
import com.bitresolution.xtest.core.lifecycle.Phase;
import com.bitresolution.xtest.events.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.bitresolution.xtest.core.events.CompleteEvent.complete;
import static com.bitresolution.xtest.core.events.StartEvent.start;

@Component
public class GenerateSourcesPhase implements Phase<Void, Sources> {

    private static final Logger log = LoggerFactory.getLogger(GenerateSourcesPhase.class);

    private final Publisher publisher;
    private final SourceBuilderFactory factory;
    private final SourceConfiguration configuration;

    @Autowired
    public GenerateSourcesPhase(Publisher publisher, SourceBuilderFactory factory, SourceConfiguration configuration) {
        this.publisher = publisher;
        this.factory = factory;
        this.configuration = configuration;
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
        publisher.publish(start(this));
        Sources sources = factory.create()
                .includeClassSources(configuration.getIncludedClasses())
                .includePackageSources(configuration.getIncludedPackages())
                .excludeClassSources(configuration.getExcludedClasses())
                .excludePackageSources(configuration.getExcludedPackages())
                .build();
        publisher.publish(complete(this));
        return sources;
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
