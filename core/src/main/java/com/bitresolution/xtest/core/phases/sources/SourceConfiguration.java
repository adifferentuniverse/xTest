package com.bitresolution.xtest.core.phases.sources;

import com.beust.jcommander.Parameter;
import com.bitresolution.xtest.cli.ClassSourceConverter;

import java.util.ArrayList;
import java.util.List;

public class SourceConfiguration {

    @Parameter(
            names = {"-class","-classes"},
            variableArity = true,
            converter = ClassSourceConverter.class,
            description = "class names to be executed")
    private List<ClassSource> includedClasses;

    @Parameter(
            names = "-excludeclasses",
            variableArity = true,
            converter = ClassSourceConverter.class,
            description ="class names to exclude")
    private List<ClassSource> excludedClasses;

    public SourceConfiguration() {
        this.includedClasses = new ArrayList<ClassSource>();
        this.excludedClasses = new ArrayList<ClassSource>();
    }

    public List<ClassSource> getIncludedClasses() {
        return includedClasses;
    }

    public void setIncludedClasses(List<ClassSource> includedClasses) {
        this.includedClasses = includedClasses;
    }

    public List<ClassSource> getExcludedClasses() {
        return excludedClasses;
    }

    public void setExcludedClasses(List<ClassSource> excludedClasses) {
        this.excludedClasses = excludedClasses;
    }
}