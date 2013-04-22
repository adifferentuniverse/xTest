package com.bitresolution.xtest.core.graph.nodes;

import com.bitresolution.xtest.core.graph.relationships.Relationship;
import com.bitresolution.xtest.core.graph.TestGraph;
import com.bitresolution.xtest.core.graph.TestGraphException;
import org.javatuples.Pair;

import java.util.Set;

public interface Node<T> {

    T getValue();

    TestGraph getTestGraph();

    void addNode(Node node, Relationship relationship) throws TestGraphException;

    Set<Node<?>> getAdjacentNodesByRelationship(Relationship relationship);

    Set<Node<?>> getAdjacentNodes();
}
