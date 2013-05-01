package com.bitresolution.xtest.examples;

import com.bitresolution.xtest.Node;
import com.bitresolution.xtest.Promises;

import static com.bitresolution.xtest.PromisesBuilder.promise;

@Node
public class TestNodeSingleMethodExample {

    @Node
    public Promises shouldTestSomething() {
        return promise().that().and();
    }
}
