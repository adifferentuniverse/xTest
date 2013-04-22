package com.bitresolution.xtest.examples;

import com.bitresolution.xtest.Result;
import com.bitresolution.xtest.annotation.TestNode;

import static com.bitresolution.xtest.ResultBuilder.expect;

@TestNode
public class TestNodeMultipleMethodExample {

    @TestNode
    public Result shouldTestA() {
        return expect().that().and();
    }

    @TestNode
    public Result shouldTestB() {
        return expect().that().and();
    }

    @TestNode
    public Result shouldTestC() {
        return expect().that().and();
    }
}
