package com.bitresolution.xtest.core.phases.compile.nodes;

import com.google.common.base.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;

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
    public String toString() {
        return new ToStringBuilder(this)
                .append("value", value)
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final BaseNode other = (BaseNode) obj;
        return Objects.equal(this.value, other.value);
    }
}
