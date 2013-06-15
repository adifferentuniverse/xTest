package com.bitresolution.xtest.core.lifecycle;

public interface Phase<I, O> {

    Class<I> getInputType();
    Class<O> getOutputType();

    O execute(I inpur);
}
