package com.bitresolution.xtest.examples;

import com.bitresolution.xtest.Node;
import com.bitresolution.xtest.Promises;

import static com.bitresolution.xtest.PromisesBuilder.promise;

@Node
public class TestNodeMultipleMethodExample {

    @Node
    public Promises shouldTestA() {
        return promise().that().and();
    }

    @Node
    public Promises shouldTestB() {
        return promise().that().and();
    }

    @Node
    public Promises shouldTestC() {
        return promise().that().and();
    }
}
