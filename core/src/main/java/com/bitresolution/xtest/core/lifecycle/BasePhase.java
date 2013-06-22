package com.bitresolution.xtest.core.lifecycle;

import com.google.common.base.Objects;

public abstract class BasePhase<I, O> implements Phase<I, O> {

    private final Class<I> inputType;
    private final Class<O> outputType;

    protected BasePhase(Class<I> inputType, Class<O> outputType) {
        this.inputType = inputType;
        this.outputType = outputType;
    }

    @Override
    public Class<I> getInputType() {
        return inputType;
    }

    @Override
    public Class<O> getOutputType() {
        return outputType;
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
        if(obj == null || obj.getClass().isInstance(this)) {
            return false;
        }
        final BasePhase other = (BasePhase) obj;
        return Objects.equal(this.inputType, other.inputType) && Objects.equal(this.outputType, other.outputType);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("inputType", inputType)
                .add("outputType", outputType)
                .toString();
    }
}
