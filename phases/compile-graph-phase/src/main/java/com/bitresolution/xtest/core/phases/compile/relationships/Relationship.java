package com.bitresolution.xtest.core.phases.compile.relationships;

import com.bitresolution.xtest.core.phases.compile.nodes.XNode;

import javax.validation.constraints.NotNull;

public interface Relationship<S,D> {

    @NotNull
    XNode<S> getSource();
    @NotNull
    XNode<D> getDestination();
}
