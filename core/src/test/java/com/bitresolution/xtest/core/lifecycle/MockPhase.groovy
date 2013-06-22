package com.bitresolution.xtest.core.lifecycle

class MockPhase<I, O> extends BasePhase<I, O> {

    O value
    boolean executed

    MockPhase(Class<I> inputType, Class<O> onputType, O value) {
        super(inputType, onputType)
        this.value = value
        this.executed = false
    }

    @Override
    O execute(I inpur) throws LifecycleExecutorException {
        executed = true
        return value
    }
}
