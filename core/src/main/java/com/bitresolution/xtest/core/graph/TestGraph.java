package com.bitresolution.xtest.core.graph;

import com.bitresolution.xtest.core.graph.nodes.XNode;
import com.bitresolution.xtest.core.graph.relationships.Relationship;

import java.util.Set;

public interface TestGraph {
    XNode getRootNode();

    void addNode(XNode<?> source, XNode<?> destination, Relationship relationship) throws TestGraphException;

    Set<XNode<?>> getAdjacentNodesByRelationship(XNode<?> node, Class<? extends Relationship> relationship);

    Set<XNode<?>> getAdjacentNodes(XNode<?> tBaseNode);

    boolean contains(XNode<?> node);

    void addRelationship(XNode<?> source, XNode<?> destination, Relationship relationship) throws TestGraphException;

    void addNode(XNode source) throws TestGraphException;

    boolean contains(Relationship relationship);

    Set<Relationship> getInboundRelationships(XNode<?> node);

    Set<Relationship> getOutboundRelationships(XNode<?> node);

    void removeRelationship(Relationship p);
}
