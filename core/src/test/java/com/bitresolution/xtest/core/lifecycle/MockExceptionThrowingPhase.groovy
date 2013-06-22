package com.bitresolution.xtest.core.lifecycle

class MockExceptionThrowingPhase<I, O> extends BasePhase<I, O> {

    MockExceptionThrowingPhase(Class<I> inputType, Class<O> onputType) {
        super(inputType, onputType)
    }

    @Override
    O execute(I inpur) throws LifecycleExecutorException {
        throw new LifecycleExecutorException("Error")
    }
}
