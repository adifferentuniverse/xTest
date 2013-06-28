package com.bitresolution.xtest.core.phases.compile.nodes;

public class Root {

    public static final Root ROOT = new Root();

    private Root() {
    }

    @Override
    public int hashCode() {
        return 42; //singleton and should only be one instance per graph
    }

    @Override
    public boolean equals(Object o) {
        return o == this;
    }

    @Override
    public String toString() {
        return "Root Node";
    }
}
