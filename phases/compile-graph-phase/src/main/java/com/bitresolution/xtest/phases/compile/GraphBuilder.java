package com.bitresolution.xtest.phases.compile;

import com.bitresolution.xtest.phases.sources.Sources;

import javax.validation.constraints.NotNull;

public interface GraphBuilder {

    @NotNull
    GraphBuilder add(@NotNull Sources input) throws CompileGraphException;

    @NotNull
    TestGraph build();
}
