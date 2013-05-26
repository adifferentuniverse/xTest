package com.bitresolution.xtest.core.graph.nodes;

import java.lang.reflect.Method;

public class MethodNode extends BaseNode<Method> implements XNode<Method> {

    public MethodNode(Method value) {
        super(value);
    }
}
