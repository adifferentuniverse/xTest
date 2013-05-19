package com.bitresolution.xtest.core.graph;

import com.bitresolution.xtest.core.graph.nodes.Node;
import com.bitresolution.xtest.core.graph.relationships.Relationship;

import java.util.Set;

public interface TestGraph {
    Node getRootNode();

    void addNode(Node<?> source, Node<?> destination, Relationship relationship) throws TestGraphException;

    Set<Node<?>> getAdjacentNodesByRelationship(Node<?> node, Class<? extends Relationship> relationship);

    Set<Node<?>> getAdjacentNodes(Node<?> tBaseNode);

    boolean contains(Node<?> node);

    void addRelationship(Node<?> source, Node<?> destination, Relationship relationship) throws TestGraphException;

    void addNode(Node source) throws TestGraphException;

    boolean contains(Relationship relationship);

    Set<Relationship> getInboundRelationships(Node<?> node);

    Set<Relationship> getOutboundRelationships(Node<?> node);

    void removeRelationship(Relationship p);
}
