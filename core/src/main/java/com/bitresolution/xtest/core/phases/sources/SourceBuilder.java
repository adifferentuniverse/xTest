package com.bitresolution.xtest.core.phases.sources;

import com.bitresolution.succor.reflection.FullyQualifiedClassName;

import java.util.List;

public interface SourceBuilder {
    SourceBuilder includeClassSources(List<FullyQualifiedClassName> classNames);

    SourceBuilder excludeClassSources(List<FullyQualifiedClassName> classNames);

    Sources build();
}
