package com.bitresolution.xtest.core.lifecycle;

public class MockExceptionThrowingPhase<I, O> extends MockPhase<I, O> {

    public MockExceptionThrowingPhase(Class<I> inputType, Class<O> onputType) {
        super(inputType, onputType, null);
    }

    @Override
    public O execute(I input) throws LifecycleExecutorException {
        throw new LifecycleExecutorException("error");
    }

    @Override
    public String getName() {
        return "mock-exception-throwing-phase";
    }

}
