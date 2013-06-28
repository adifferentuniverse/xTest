package com.bitresolution.xtest.core.phases.compile.relationships;

import com.bitresolution.xtest.core.phases.compile.nodes.XNode;

public class ContainsRelationship<S, D> extends BaseRelationship<S, D> {
    public ContainsRelationship(XNode<S> source, XNode<D> destination) {
        super(source, destination);
    }
}
