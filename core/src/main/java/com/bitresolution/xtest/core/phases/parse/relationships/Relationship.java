package com.bitresolution.xtest.core.phases.parse.relationships;

import com.bitresolution.xtest.core.phases.parse.nodes.XNode;

public interface Relationship<S,D> {

    XNode<S> getSource();
    XNode<D> getDestination();
}
