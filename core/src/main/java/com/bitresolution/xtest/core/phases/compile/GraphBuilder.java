package com.bitresolution.xtest.core.phases.compile;

import com.bitresolution.xtest.core.phases.sources.Sources;

public interface GraphBuilder {

    GraphBuilder add(Sources input);

    TestGraph build();
}
