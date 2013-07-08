package com.bitresolution.xtest.core.phases.sources;

import java.util.List;

public interface SourceBuilder {
    SourceBuilder includeClassSources(List<ClassSource> sources);

    SourceBuilder excludeClassSources(List<ClassSource> sources);

    Sources build();
}
