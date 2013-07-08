package com.bitresolution.xtest.core;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParametersDelegate;
import com.bitresolution.succor.reflection.FullyQualifiedClassName;
import com.bitresolution.xtest.XTestDefaultContext;
import com.bitresolution.xtest.cli.ClassSourceConverter;
import com.bitresolution.xtest.core.phases.sources.SourceConfiguration;

public class XTestConfiguration {

    @Parameter(
            names = "-configurationClass",
            converter = ClassSourceConverter.class,
            description = "configuration class name")
    private FullyQualifiedClassName configurationClass = new FullyQualifiedClassName(XTestDefaultContext.class);

    @ParametersDelegate
    private SourceConfiguration sourceConfiguration = new SourceConfiguration();

    @Parameter(names = "-log", description = "Level of verbosity")
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