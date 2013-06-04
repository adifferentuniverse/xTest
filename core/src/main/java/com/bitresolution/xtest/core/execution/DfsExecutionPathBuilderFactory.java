package com.bitresolution.xtest.core.execution;

public class DfsExecutionPathBuilderFactory implements ExecutionPathBuilderFactory {
    @Override
    public ExecutionPathBuilder getBuilder() {
        return new DfsExecutionPathBuilder();
    }
}
