package com.bitresolution.xtest.core.lifecycle;

import com.bitresolution.xtest.XTestDefaultContext;
import com.bitresolution.xtest.core.SourceConfiguration;
import org.springframework.context.annotation.Bean;

public class EngineTestConfiguration extends XTestDefaultContext {

    private final Lifecycle lifecycle;
    private final SourceConfiguration sourceConfiguration;

    public EngineTestConfiguration() throws LifecycleExecutorException {
        this.lifecycle = buildLifecycle();
        this.sourceConfiguration = buildSourceConfiguration();
    }

    @Bean
    public Lifecycle lifecycle() throws LifecycleExecutorException {
        return lifecycle;
    }

    @Bean
    public SourceConfiguration sourceConfiguration() {
        return sourceConfiguration;
    }

    private Lifecycle buildLifecycle() throws LifecycleExecutorException {
        Lifecycle lifecycle = new Lifecycle();
        MockPhase<Void, Integer> phaseA = new MockPhase<Void, Integer>(Void.class, Integer.class, 1);
        MockPhase<Integer, Integer> phaseB = new MockPhase<Integer, Integer>(Integer.class, Integer.class, 2);
        MockPhase<Integer, String> phaseC = new MockPhase<Integer, String>(Integer.class, String.class, "3");

        lifecycle.add(phaseA);
        lifecycle.add(phaseB);
        lifecycle.add(phaseC);

        return lifecycle;
    }

    private SourceConfiguration buildSourceConfiguration() {
        return new SourceConfiguration();
    }
}
