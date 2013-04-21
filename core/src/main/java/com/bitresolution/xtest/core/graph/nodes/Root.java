package com.bitresolution.xtest.core.graph.nodes;

import com.bitresolution.xtest.core.graph.TestGraph;

import java.util.List;
import java.util.Set;

public class Root extends BaseNode<Set<Node<?>>> implements Node<Set<Node<?>> {

    public Root(TestGraph testGraph) {
        super(null, testGraph);
    }

    @Override
    public Set<Node<?>> getValue() {
        return getTestGraph().;
    }
}
