package com.bitresolution.xtest.core;

import com.bitresolution.succor.reflection.FullyQualifiedClassName;
import com.bitresolution.xtest.core.lifecycle.Phase;

import java.util.HashMap;
import java.util.Map;

public class XTestConfiguration {

    private FullyQualifiedClassName configurationClass;

    private String logLevel;

    private Map<Class<? extends Phase<?, ?>>, PhaseConfiguration> phaseConfigurations = new HashMap<Class<? extends Phase<?, ?>>, PhaseConfiguration>();

    public FullyQualifiedClassName getConfigurationClass() {
        return configurationClass;
    }

    public void setConfigurationClass(FullyQualifiedClassName configurationClass) {
        this.configurationClass = configurationClass;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public Map<Class<? extends Phase<?, ?>>, PhaseConfiguration> getPhaseConfigurations() {
        return phaseConfigurations;
    }

    public void setPhaseConfigurations(Map<Class<? extends Phase<?, ?>>, PhaseConfiguration> phaseConfigurations) {
        this.phaseConfigurations = phaseConfigurations;
    }

    public void addPhaseConfiguration(PhaseConfiguration configuration) {
        phaseConfigurations.put(configuration.getPhase(), configuration);
    }
}