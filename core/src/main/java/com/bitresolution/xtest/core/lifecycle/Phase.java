package com.bitresolution.xtest.core.lifecycle;

import javax.validation.constraints.NotNull;

public interface Phase<I, O> {

    @NotNull
    Class<I> getInputType();
    @NotNull
    Class<O> getOutputType();

    @NotNull
    O execute(@NotNull I input) throws LifecycleExecutorException;

    @NotNull
    String getName();
}
