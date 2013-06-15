package com.bitresolution.xtest.core.phases.parse.nodes;

import com.bitresolution.xtest.commons.reflection.FullyQualifiedClassName;

public class ClassNode extends BaseNode<FullyQualifiedClassName> implements XNode<FullyQualifiedClassName> {

    public ClassNode(FullyQualifiedClassName fqcn) {
        super(fqcn);
    }
}
