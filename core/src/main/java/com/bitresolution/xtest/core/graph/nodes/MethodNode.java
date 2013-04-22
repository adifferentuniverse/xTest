package com.bitresolution.xtest.core.graph.nodes;

import com.bitresolution.xtest.core.graph.TestGraph;

import java.lang.reflect.Method;

public class MethodNode extends BaseNode<Method> implements Node<Method> {

    public MethodNode(Method value, TestGraph testGraph) {
        super(value, testGraph);
    }
}
