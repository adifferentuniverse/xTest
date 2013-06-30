package com.bitresolution.xtest.core.phases.sources;

import com.bitresolution.succor.reflection.FullyQualifiedClassName;
import com.bitresolution.succor.reflection.PackageName;

import java.util.List;
import java.util.Set;

public class SourceBuilder {

    private Set<Source> includedSources;
    private Set<Source> excludedSources;

    public SourceBuilder includeClassSource(String className) {
        ClassSource source = new ClassSource(new FullyQualifiedClassName(className));
        includedSources.add(source);
        return this;
    }

    public SourceBuilder includeClassSources(List<String> classNames) {
        for(String className : classNames) {
            includeClassSource(className);
        }
        return this;
    }

    public SourceBuilder includePackageSource(String packageName) {
        PackageSource source = new PackageSource(new PackageName(packageName));
        includedSources.add(source);
        return this;
    }

    public SourceBuilder includePackageSources(List<String> packageNames) {
        for(String packageName : packageNames) {
            includePackageSource(packageName);
        }
        return this;
    }

    public SourceBuilder excludeClassSource(String className) {
        ClassSource source = new ClassSource(new FullyQualifiedClassName(className));
        excludedSources.add(source);
        return this;
    }

    public SourceBuilder excludeClassSources(List<String> classNames) {
        for(String className : classNames) {
            excludeClassSource(className);
        }
        return this;
    }

    public SourceBuilder excludePackageSource(String packageName) {
        PackageSource source = new PackageSource(new PackageName(packageName));
        excludedSources.add(source);
        return this;
    }

    public SourceBuilder excludePackageSources(List<String> packageNames) {
        for(String packageName : packageNames) {
            excludePackageSource(packageName);
        }
        return this;
    }

    public Sources build() {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }
}
