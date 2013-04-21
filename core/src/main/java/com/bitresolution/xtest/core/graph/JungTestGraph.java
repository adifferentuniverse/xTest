package com.bitresolution.xtest.core.graph;

import com.bitresolution.xtest.core.TestGraph;
import com.bitresolution.xtest.core.graph.Node;
import com.bitresolution.xtest.core.graph.Relationship;
import edu.uci.ics.jung.graph.Graph;

public class JungTestGraph implements TestGraph {

    private final Graph<Node, Relationship> graph;

    public JungTestGraph() {
        graph = null;
    }
}
