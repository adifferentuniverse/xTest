package com.bitresolution.xtest.core.graph.relationships;

import com.bitresolution.xtest.core.graph.nodes.XNode;

public class ContainsRelationship<S, D> extends BaseRelationship<S, D> {
    public ContainsRelationship(XNode<S> source, XNode<D> destination) {
        super(source, destination);
    }
}
