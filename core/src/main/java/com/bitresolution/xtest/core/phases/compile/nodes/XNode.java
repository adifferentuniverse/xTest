package com.bitresolution.xtest.core.phases.compile.nodes;

import javax.validation.constraints.NotNull;

public interface XNode<T> {

    @NotNull
    T getValue();
}
