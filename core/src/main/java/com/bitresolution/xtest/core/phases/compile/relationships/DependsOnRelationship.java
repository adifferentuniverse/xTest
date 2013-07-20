package com.bitresolution.xtest.core.phases.compile.relationships;

import com.bitresolution.xtest.core.phases.compile.nodes.XNode;

import javax.validation.constraints.NotNull;

public class DependsOnRelationship<S, D> extends BaseRelationship<S, D> {
    public DependsOnRelationship(@NotNull XNode<S> source, @NotNull XNode<D> destination) {
        super(source, destination);
    }
}
