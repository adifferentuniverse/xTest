package com.bitresolution.xtest;

public class ResultBuilder implements Result {

    public static ResultBuilder expect() {
        return new ResultBuilder();
    }

    public ResultBuilder that() {
        return this;
    }

    public ResultBuilder and() {
        return this;
    }
}
