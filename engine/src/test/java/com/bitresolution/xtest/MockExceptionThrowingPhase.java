package com.bitresolution.xtest;

import com.bitresolution.xtest.core.lifecycle.LifecycleExecutorException;

import javax.validation.constraints.NotNull;

public class MockExceptionThrowingPhase<I, O> extends MockPhase<I, O> {

    public MockExceptionThrowingPhase(Class<I> inputType, Class<O> onputType) {
        super(inputType, onputType, null);
    }

    @NotNull
    @Override
    public O execute(@NotNull I input) throws LifecycleExecutorException {
        throw new LifecycleExecutorException("error");
    }

    @NotNull
    @Override
    public String getName() {
        return "mock-exception-throwing-phase";
    }

}
