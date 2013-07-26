package com.bitresolution.xtest.core.phases.compile.nodes;

import com.bitresolution.succor.reflection.FullyQualifiedClassName;

import javax.validation.constraints.NotNull;

public class ClassNode extends GenericNode<FullyQualifiedClassName> implements XNode<FullyQualifiedClassName> {

    public ClassNode(@NotNull FullyQualifiedClassName fqcn) {
        super(fqcn);
    }
}
