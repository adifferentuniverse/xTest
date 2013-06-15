package com.bitresolution.xtest.core.phases.parse;

import com.bitresolution.xtest.core.phases.parse.nodes.XNode;
import com.bitresolution.xtest.core.phases.parse.relationships.Relationship;

import java.util.Set;

public interface TestGraph {
    XNode getRootNode();

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
