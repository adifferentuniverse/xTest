package com.bitresolution.xtest.core.graph.relationships;

import com.bitresolution.xtest.core.graph.nodes.XNode;

public class DependsOnRelationship<S, D> extends BaseRelationship<S, D> {
    public DependsOnRelationship(XNode<S> source, XNode<D> destination) {
        super(source, destination);
    }
}
