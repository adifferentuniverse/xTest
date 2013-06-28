package com.bitresolution.xtest.core.phases.compile.nodes;

import com.bitresolution.succor.reflection.FullyQualifiedClassName;

public class ClassNode extends BaseNode<FullyQualifiedClassName> implements XNode<FullyQualifiedClassName> {

    public ClassNode(FullyQualifiedClassName fqcn) {
        super(fqcn);
    }
}
