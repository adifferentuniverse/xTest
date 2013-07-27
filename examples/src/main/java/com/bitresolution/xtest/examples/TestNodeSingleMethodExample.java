package com.bitresolution.xtest.examples;

import com.bitresolution.xtest.Node;
import com.bitresolution.xtest.Promises;

import static com.bitresolution.xtest.PromisesBuilder.promise;
import static org.hamcrest.CoreMatchers.is;

@Node
public class TestNodeSingleMethodExample {

    @Node
    public Promises shouldTestSomething() {
        return promise().that(true, is(true));
    }
}
