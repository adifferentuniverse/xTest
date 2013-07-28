package com.bitresolution.xtest.core.phases.compile;

import com.bitresolution.xtest.core.phases.sources.Sources;

import javax.validation.constraints.NotNull;

public interface GraphBuilder {

    @NotNull
    GraphBuilder add(@NotNull Sources input) throws CompileGraphException;

    @NotNull
    TestGraph build();
}
