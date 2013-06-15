package com.bitresolution.xtest.core.phases.parse.nodes;

import java.lang.reflect.Method;

public class MethodNode extends BaseNode<Method> implements XNode<Method> {

    public MethodNode(Method value) {
        super(value);
    }
}
