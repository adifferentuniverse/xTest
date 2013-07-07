package com.bitresolution.xtest.core.phases.sources;

import com.beust.jcommander.Parameter;
import com.bitresolution.succor.reflection.FullyQualifiedClassName;
import com.bitresolution.xtest.cli.FullyQualifiedClassNameConverter;

import java.util.ArrayList;
import java.util.List;

public class SourceConfiguration {

    @Parameter(
            names = {"-class","-classes"},
            variableArity = true,
            converter = FullyQualifiedClassNameConverter.class,
            description = "class names to be executed")
    private List<FullyQualifiedClassName> includedClasses;

    @Parameter(
            names = "-excludeclasses",
            variableArity = true,
            converter = FullyQualifiedClassNameConverter.class,
            description ="class names to exclude")
    private List<FullyQualifiedClassName> excludedClasses;

    public SourceConfiguration() {
        this.includedClasses = new ArrayList<FullyQualifiedClassName>();
        this.excludedClasses = new ArrayList<FullyQualifiedClassName>();
    }

    public List<FullyQualifiedClassName> getIncludedClasses() {
        return includedClasses;
    }

    public void setIncludedClasses(List<FullyQualifiedClassName> includedClasses) {
        this.includedClasses = includedClasses;
    }

    public List<FullyQualifiedClassName> getExcludedClasses() {
        return excludedClasses;
    }

    public void setExcludedClasses(List<FullyQualifiedClassName> excludedClasses) {
        this.excludedClasses = excludedClasses;
    }
}