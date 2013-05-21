package com.bitresolution.xtest.core.graph.relationships;

import com.bitresolution.xtest.core.graph.nodes.XNode;

public interface Relationship<S,D> {

    XNode<S> getSource();
    XNode<D> getDestination();
}
