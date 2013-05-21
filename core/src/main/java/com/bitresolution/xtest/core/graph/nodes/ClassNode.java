package com.bitresolution.xtest.core.graph.nodes;

import com.bitresolution.xtest.core.graph.TestGraph;
import com.bitresolution.xtest.reflection.FullyQualifiedClassName;

public class ClassNode extends BaseNode<FullyQualifiedClassName> implements XNode<FullyQualifiedClassName> {

    public ClassNode(FullyQualifiedClassName fqcn, TestGraph testGraph) {
        super(fqcn, testGraph);
    }
}
