package com.bitresolution.xtest.core.phases.sources;

import com.bitresolution.succor.reflection.FullyQualifiedClassName;

import java.util.List;

public interface SourceBuilder {
    SourceBuilder includeClassSources(List<FullyQualifiedClassName> classNames);

    SourceBuilder includePackageSources(List<String> packageNames);

    SourceBuilder excludeClassSources(List<FullyQualifiedClassName> classNames);

    SourceBuilder excludePackageSources(List<String> packageNames);

    Sources build();
}
