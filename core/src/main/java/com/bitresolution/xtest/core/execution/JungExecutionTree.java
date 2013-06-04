package com.bitresolution.xtest.core.execution;

import edu.uci.ics.jung.graph.DelegateTree;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Tree;

public class JungExecutionTree implements ExecutionTree<Fixture, Order> {

    private final Tree<Fixture, Order> fixtures;

    public JungExecutionTree() {
        fixtures = new DelegateTree<Fixture, Order>(new DirectedSparseGraph<Fixture, Order>());
    }
}
