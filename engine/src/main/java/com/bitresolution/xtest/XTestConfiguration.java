package com.bitresolution.xtest;

import com.bitresolution.succor.reflection.FullyQualifiedClassName;
import com.bitresolution.xtest.core.XTestContext;
import com.bitresolution.xtest.core.phases.sources.SourceConfiguration;

public class XTestConfiguration {

    private FullyQualifiedClassName configurationClass = new FullyQualifiedClassName(XTestContext.class);

    private SourceConfiguration sourceConfiguration = new SourceConfiguration();

    private String logLevel;

    public FullyQualifiedClassName getConfigurationClass() {
        return configurationClass;
    }

    public void setConfigurationClass(FullyQualifiedClassName configurationClass) {
        this.configurationClass = configurationClass;
    }

    public SourceConfiguration getSourceConfiguration() {
        return sourceConfiguration;
    }

    public void setSourceConfiguration(SourceConfiguration sourceConfiguration) {
        this.sourceConfiguration = sourceConfiguration;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }
}