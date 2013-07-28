package com.bitresolution.xtest.core.phases.compile.nodes;

public class Root {

    public static final Root ROOT = new Root();

    private Root() {
    }

    @Override
    public String toString() {
        return "Root";
    }
}
