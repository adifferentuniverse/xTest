package com.bitresolution.xtest.core.phases.compile.relationships;

import com.bitresolution.xtest.core.phases.compile.nodes.XNode;

public interface Relationship<S,D> {

    XNode<S> getSource();
    XNode<D> getDestination();
}
