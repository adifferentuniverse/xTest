package com.bitresolution.xtest.core.graph.nodes;

import com.bitresolution.xtest.core.graph.TestGraph;

public class MethodNode extends BaseNode<Class<?>> implements Node<Class<?>> {

    public MethodNode(Class<?> value, TestGraph testGraph) {
        super(value, testGraph);
    }
}
