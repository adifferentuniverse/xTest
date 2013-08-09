package com.bitresolution.xtest.core.phases.compile.relationships;

import com.bitresolution.xtest.core.phases.compile.nodes.XNode;
import com.google.common.base.Objects;

import javax.validation.constraints.NotNull;

abstract class BaseRelationship<S,D> implements Relationship<S, D> {

    @NotNull
    private final XNode<S> source;
    @NotNull
    private final XNode<D> destination;

    public BaseRelationship(@NotNull XNode<S> source, @NotNull XNode<D> destination) {
        this.source = source;
        this.destination = destination;
    }

    @Override
    @NotNull
    public XNode<S> getSource() {
        return source;
    }

    @Override
    @NotNull
    public XNode<D> getDestination() {
        return destination;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(source, destination);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final BaseRelationship<?, ?> other = (BaseRelationship<?, ?>) obj;
        return Objects.equal(this.source, other.source)
                && Objects.equal(this.destination, other.destination);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("source", source)
                .add("destination", destination)
                .toString();
    }
}
