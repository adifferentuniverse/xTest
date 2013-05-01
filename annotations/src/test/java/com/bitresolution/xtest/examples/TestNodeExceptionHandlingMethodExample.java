package com.bitresolution.xtest.examples;

import com.bitresolution.xtest.Promises;
import com.bitresolution.xtest.annotation.TestNode;

import static com.bitresolution.xtest.PromisesBuilder.promise;

@TestNode
public class TestNodeExceptionHandlingMethodExample {

    @TestNode
    public Promises shouldTestSomething() {
        return promise().that().and();
    }
}
