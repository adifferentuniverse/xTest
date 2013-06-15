package com.bitresolution.xtest.core.phases.parse.relationships;

import com.bitresolution.xtest.core.phases.parse.nodes.XNode;

public class DependsOnRelationship<S, D> extends BaseRelationship<S, D> {
    public DependsOnRelationship(XNode<S> source, XNode<D> destination) {
        super(source, destination);
    }
}
