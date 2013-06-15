package com.bitresolution.xtest.core.phases.parse.relationships;

import com.bitresolution.xtest.core.phases.parse.nodes.XNode;
import com.google.common.base.Objects;

abstract class BaseRelationship<S,D> implements Relationship<S, D> {
    private final XNode<S> source;
    private final XNode<D> destination;

    public BaseRelationship(XNode<S> source, XNode<D> destination) {
        this.source = source;
        this.destination = destination;
    }

    @Override
    public XNode<S> getSource() {
        return source;
    }

    @Override
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
        final BaseRelationship other = (BaseRelationship) obj;
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
