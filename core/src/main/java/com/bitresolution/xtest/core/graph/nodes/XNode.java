package com.bitresolution.xtest.core.graph.nodes;

import com.bitresolution.xtest.core.graph.relationships.Relationship;
import com.bitresolution.xtest.core.graph.TestGraph;
import com.bitresolution.xtest.core.graph.TestGraphException;

import java.util.Set;

public interface XNode<T> {

    T getValue();

    TestGraph getTestGraph();

    void addNode(XNode node, Relationship relationship) throws TestGraphException;

    Set<XNode<?>> getAdjacentNodesByRelationship(Class<? extends Relationship> relationship);

    Set<XNode<?>> getAdjacentNodes();
}
