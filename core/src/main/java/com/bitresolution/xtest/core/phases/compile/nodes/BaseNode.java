package com.bitresolution.xtest.core.phases.compile.nodes;

import com.google.common.base.Objects;

public class BaseNode<T> implements XNode<T> {

    private final T value;

    public BaseNode(T value) {
        this.value = value;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj instanceof BaseNode) {
            final BaseNode other = (BaseNode) obj;
            return Objects.equal(this.value, other.value);
        }
        return false;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("value", value)
                .toString();
    }
}
