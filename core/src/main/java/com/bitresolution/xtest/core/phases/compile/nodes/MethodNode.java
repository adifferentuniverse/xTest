package com.bitresolution.xtest.core.phases.compile.nodes;

import com.bitresolution.succor.reflection.FullyQualifiedMethodName;

import javax.validation.constraints.NotNull;

public class MethodNode extends BaseNode<FullyQualifiedMethodName> implements XNode<FullyQualifiedMethodName> {

    public MethodNode(@NotNull FullyQualifiedMethodName value) {
        super(value);
    }
}
