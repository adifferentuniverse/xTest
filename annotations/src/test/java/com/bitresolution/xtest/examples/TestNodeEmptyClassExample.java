package com.bitresolution.xtest.examples;

import com.bitresolution.xtest.Result;
import com.bitresolution.xtest.annotation.TestNode;

import static com.bitresolution.xtest.ResultBuilder.expect;

@TestNode
public class TestNodeEmptyClassExample {

    @TestNode
    public Result shouldTestSomething() {
        return expect().that().and();
    }
}
