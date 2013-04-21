package com.bitresolution.xtest.core.graph.nodes;

import com.bitresolution.xtest.core.graph.Relationship;
import com.bitresolution.xtest.core.graph.TestGraph;
import com.bitresolution.xtest.core.graph.TestGraphException;

public interface Node {
    TestGraph getTestGraph();

    void addNode(Node node, Relationship relationship) throws TestGraphException;
}
