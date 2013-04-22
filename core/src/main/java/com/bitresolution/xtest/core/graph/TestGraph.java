package com.bitresolution.xtest.core.graph;

import com.bitresolution.xtest.core.graph.nodes.BaseNode;
import com.bitresolution.xtest.core.graph.nodes.Node;
import com.bitresolution.xtest.core.graph.relationships.Relationship;
import org.javatuples.Pair;

import java.util.Set;

public interface TestGraph {
    Node getRootNode();

    void addNode(Node<?> source, Node<?> destination, Relationship relationship) throws TestGraphException;

    Set<Node<?>> getAdjacentNodesByRelationship(Node<?> node, Relationship relationship);

    Set<Node<?>> getAdjacentNodesp(Node<?> tBaseNode);

    boolean contains(Node<?> node);

    void addRelationship(Node<?> source, Node<?> destination, Relationship relationship) throws TestGraphException;
}
