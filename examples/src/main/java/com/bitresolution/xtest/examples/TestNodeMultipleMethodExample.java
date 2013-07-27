package com.bitresolution.xtest.examples;

import com.bitresolution.xtest.Node;
import com.bitresolution.xtest.Promises;

import static com.bitresolution.xtest.PromisesBuilder.promise;
import static org.hamcrest.CoreMatchers.is;

@Node
public class TestNodeMultipleMethodExample {

    @Node
    public Promises shouldTestA() {
        return promise()
                .that(true, is(true))
                .and(false, is(false));
    }

    @Node
    public Promises shouldTestB() {
        return promise()
                .that(true, is(true))
                .and(false, is(false));
    }

    @Node
    public Promises shouldTestC() {
        return promise()
                .that(true, is(true))
                .and(false, is(false));
    }
}
