package com.bitresolution.xtest.core.lifecycle;

import com.google.common.base.Objects;

import javax.validation.constraints.NotNull;

public class MockPhase<I, O> implements Phase<I, O> {

    private final Class<I> inputType;
    private final Class<O> outputType;
    private final O value;
    private boolean executed;

    public MockPhase(Class<I> inputType, Class<O> onputType, O value) {
        this.inputType = inputType;
        this.outputType = onputType;
        this.value = value;
        this.executed = false;
    }

    @NotNull
    @Override
    public O execute(@NotNull I input) throws LifecycleExecutorException {
        executed = true;
        return value;
    }

    @NotNull
    @Override
    public String getName() {
        return "mock-phase";
    }

    @NotNull
    public Class<I> getInputType() {
        return inputType;
    }

    @NotNull
    public Class<O> getOutputType() {
        return outputType;
    }

    public boolean isExecuted() {
        return executed;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(inputType, outputType);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }

        if(obj instanceof MockPhase) {
            final MockPhase<?, ?> other = (MockPhase<?, ?>) obj;
            return Objects.equal(this.inputType, other.inputType) && Objects.equal(this.outputType, other.outputType);
        }

        return false;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("inputType", inputType).add("outputType", outputType).toString();
    }
}
