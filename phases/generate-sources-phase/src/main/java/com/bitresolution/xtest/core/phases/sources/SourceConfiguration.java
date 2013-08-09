package com.bitresolution.xtest.core.phases.sources;

import com.bitresolution.xtest.core.PhaseConfiguration;

import java.util.ArrayList;
import java.util.List;

public class SourceConfiguration implements PhaseConfiguration {

    private List<ClassSource> includedClasses;

    private List<ClassSource> excludedClasses;

    private List<XTestAnnotatedSource> includedPackages;

    private List<XTestAnnotatedSource> excludedPackages;

    public SourceConfiguration() {
        this.includedClasses = new ArrayList<ClassSource>();
        this.excludedClasses = new ArrayList<ClassSource>();
        this.includedPackages = new ArrayList<XTestAnnotatedSource>();
        this.excludedPackages = new ArrayList<XTestAnnotatedSource>();
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

    public List<XTestAnnotatedSource> getIncludedPackages() {
        return includedPackages;
    }

    public void setIncludedPackages(List<XTestAnnotatedSource> includedPackages) {
        this.includedPackages = includedPackages;
    }

    public List<XTestAnnotatedSource> getExcludedPackages() {
        return excludedPackages;
    }

    public void setExcludedPackages(List<XTestAnnotatedSource> excludedPackages) {
        this.excludedPackages = excludedPackages;
    }

    @Override
    public Class<GenerateSourcesPhase> getPhase() {
        return GenerateSourcesPhase.class;
    }
}