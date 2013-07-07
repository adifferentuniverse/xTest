package com.bitresolution.xtest.core.phases.execute;

import com.beust.jcommander.Parameter;

public class ExecutionConfiguration {

    @Parameter(names = "-failurepolicy", description = "Failure policy (skip or continue)")
    public String failurePolicy;

    @Parameter(names = "-threadcount", description = "Number of threads to for dispatching events")
    public Integer threadCount = 1;

}
