package com.bitresolution.xtest.core.phases.sources;

import java.util.List;

public interface SourceBuilder {
    SourceBuilder include(List<? extends Source> sources);
    SourceBuilder exclude(List<? extends Source> sources);

    Sources build();
}
