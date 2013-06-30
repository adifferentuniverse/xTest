package com.bitresolution.xtest.core;

import java.util.ArrayList;
import java.util.List;

public class SourceConfiguration {
    
    private List<String> includedClasses;
    private List<String> excludedClasses;
    private List<String> includedPackages;
    private List<String> excludedPackages;

    public SourceConfiguration() {
        includedClasses = new ArrayList<String>();
        excludedClasses = new ArrayList<String>();
        includedPackages = new ArrayList<String>();
        excludedPackages = new ArrayList<String>();
    }

    public List<String> getIncludedClasses() {
        return includedClasses;
    }

    public void setIncludedClasses(List<String> includedClasses) {
        this.includedClasses = includedClasses;
    }

    public List<String> getExcludedClasses() {
        return excludedClasses;
    }

    public void setExcludedClasses(List<String> excludedClasses) {
        this.excludedClasses = excludedClasses;
    }

    public List<String> getIncludedPackages() {
        return includedPackages;
    }

    public void setIncludedPackages(List<String> includedPackages) {
        this.includedPackages = includedPackages;
    }

    public List<String> getExcludedPackages() {
        return excludedPackages;
    }

    public void setExcludedPackages(List<String> excludedPackages) {
        this.excludedPackages = excludedPackages;
    }
}
