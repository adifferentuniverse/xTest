package com.bitresolution.xtest.core.graph.nodes;

import com.bitresolution.xtest.core.graph.TestGraph;

import java.util.List;
import java.util.Set;

public class Root {

    public static Root ROOT = new Root();

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
