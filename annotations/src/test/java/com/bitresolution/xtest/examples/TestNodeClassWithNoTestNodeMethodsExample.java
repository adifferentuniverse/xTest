package com.bitresolution.xtest.examples;

import com.bitresolution.xtest.annotation.TestNode;

@TestNode
public class TestNodeClassWithNoTestNodeMethodsExample {

    public void someMethod() {
        throw new RuntimeException("Should never be executed, used in test code only");
    }
}
