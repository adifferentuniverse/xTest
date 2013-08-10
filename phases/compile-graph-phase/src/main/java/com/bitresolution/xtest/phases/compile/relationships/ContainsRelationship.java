package com.bitresolution.xtest.phases.compile.relationships;

import com.bitresolution.xtest.phases.compile.nodes.XNode;

import javax.validation.constraints.NotNull;

public class ContainsRelationship<S, D> extends BaseRelationship<S, D> {
    public ContainsRelationship(@NotNull XNode<S> source, @NotNull XNode<D> destination) {
        super(source, destination);
    }
}
