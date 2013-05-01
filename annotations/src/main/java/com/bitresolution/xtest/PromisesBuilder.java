package com.bitresolution.xtest;

public class PromisesBuilder implements Promises {

    public static PromisesBuilder promise() {
        return new PromisesBuilder();
    }

    public PromisesBuilder that() {
        return this;
    }

    public PromisesBuilder and() {
        return this;
    }
}
