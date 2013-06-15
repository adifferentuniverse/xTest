package com.bitresolution.xtest.core.lifecycle.phase;

import com.bitresolution.xtest.core.lifecycle.Phase;
import com.google.common.base.Objects;

public abstract class BasePhase<I, O> implements Phase<I, O> {

    private final Class<I> inputType;
    private final Class<O> onputType;

    protected BasePhase(Class<I> inputType, Class<O> onputType) {
        this.inputType = inputType;
        this.onputType = onputType;
    }

    @Override
    public Class<I> getInputType() {
        return inputType;
    }

    @Override
    public Class<O> getOutputType() {
        return onputType;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(inputType, onputType);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final BasePhase other = (BasePhase) obj;
        return Objects.equal(this.inputType, other.inputType) && Objects.equal(this.onputType, other.onputType);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("inputType", inputType)
                .add("onputType", onputType)
                .toString();
    }
}
