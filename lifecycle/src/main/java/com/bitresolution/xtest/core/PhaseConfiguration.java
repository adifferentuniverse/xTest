package com.bitresolution.xtest.core;

import com.bitresolution.xtest.core.lifecycle.Phase;

public interface PhaseConfiguration {

    Class<? extends Phase<?, ?>> getPhase();
}
