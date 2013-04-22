package com.bitresolution.xtest.core.graph.nodes;

import com.bitresolution.xtest.core.graph.TestGraph;

public class ClassNode extends BaseNode<Class<?>> implements Node<Class<?>> {

    public ClassNode(Class<?> value, TestGraph testGraph) {
        super(value, testGraph);
    }
}
