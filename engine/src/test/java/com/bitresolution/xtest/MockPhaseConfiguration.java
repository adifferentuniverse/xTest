package com.bitresolution.xtest;

import com.bitresolution.xtest.core.PhaseConfiguration;
import com.bitresolution.xtest.core.lifecycle.Phase;

public class MockPhaseConfiguration implements PhaseConfiguration {

    private final Class<? extends Phase<?, ?>> phaseClass;

    @SuppressWarnings({"rawtypes", "unchecked"})
    public MockPhaseConfiguration(Class<? extends Phase> phaseClass) {
        this.phaseClass = (Class<? extends Phase<?, ?>>) phaseClass;
    }

    @Override
    public Class<? extends Phase<?, ?>> getPhase() {
        return phaseClass;
    }
}
