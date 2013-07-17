package com.bitresolution.xtest.core.phases.compile.nodes;

import com.bitresolution.succor.reflection.FullyQualifiedMethodName;

public class MethodNode extends BaseNode<FullyQualifiedMethodName> implements XNode<FullyQualifiedMethodName> {

    public MethodNode(FullyQualifiedMethodName value) {
        super(value);
    }
}
