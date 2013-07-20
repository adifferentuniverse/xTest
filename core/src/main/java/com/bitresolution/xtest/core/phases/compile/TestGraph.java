package com.bitresolution.xtest.core.phases.compile;

import com.bitresolution.xtest.core.phases.compile.nodes.XNode;
import com.bitresolution.xtest.core.phases.compile.relationships.Relationship;

import java.util.Set;

public interface TestGraph {
    XNode getRootNode();

    Set<XNode> getAdjacentNodesByRelationship(XNode<?> node, Class<? extends Relationship> relationship);

    Set<XNode> getAdjacentNodes(XNode<?> tBaseNode);

    boolean contains(XNode<?> node);

    void addNode(XNode source) throws CompileGraphException;

    boolean contains(Relationship relationship);

    Set<Relationship> getInboundRelationships(XNode<?> node);

    Set<Relationship> getOutboundRelationships(XNode<?> node);

    void removeRelationship(Relationship p);

    Set<XNode> getNodes();

    Set<Relationship> getRelationships();

    <T> XNode<T> findNodeByValue(T packageName);

    void addRelationship(Relationship<?, ?> relationship) throws CompileGraphException;
}
