package com.bitresolution.xtest.core.phases.compile;

import com.bitresolution.xtest.core.phases.compile.nodes.BaseNode;
import com.bitresolution.xtest.core.phases.compile.nodes.Root;
import com.bitresolution.xtest.core.phases.compile.nodes.XNode;
import com.bitresolution.xtest.core.phases.compile.relationships.Relationship;
import com.google.common.collect.ImmutableSet;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class JungTestGraph implements TestGraph {

    private final Graph<XNode, Relationship> graph;
    private final XNode<Root> root;

    public JungTestGraph() {
        graph = new DirectedSparseMultigraph<XNode, Relationship>();
        root = new BaseNode<Root>(Root.ROOT);
        graph.addVertex(root);
    }

    @Override
    public XNode getRootNode() {
        return root;
    }

    @Override
    public void addNode(XNode source) throws TestGraphException {
        boolean nodeAdded = graph.addVertex(source);
        if(!nodeAdded) {
            throw new TestGraphException("Error adding node:" + source);
        }
    }

    @Override
    public Set<XNode<?>> getAdjacentNodesByRelationship(XNode<?> node, Class<? extends Relationship> relationship) {
        Collection<Relationship> edges = graph.getOutEdges(node);
        Set<XNode<?>> nodes = new HashSet<XNode<?>>();
        for(Relationship edge : edges) {
            if(edge.getClass().isAssignableFrom(relationship)) {
                nodes.add(graph.getDest(edge));
            }
        }
        return nodes;
    }

    @Override
    public Set<XNode<?>> getAdjacentNodes(XNode<?> node) {
        Collection<Relationship> edges = graph.getOutEdges(node);
        Set<XNode<?>> nodes = new HashSet<XNode<?>>();
        for(Relationship edge : edges) {
            nodes.add(graph.getDest(edge));
        }
        return nodes;
    }

    @Override
    public boolean contains(XNode<?> node) {
        return graph.containsVertex(node);
    }

    @Override
    public boolean contains(Relationship relationship) {
        return graph.containsEdge(relationship);
    }

    @Override
    public void addRelationship(XNode<?> source, XNode<?> destination, Relationship relationship) throws TestGraphException {
        if(!contains(source)) {
            throw new TestGraphException("Source node '{}' not in graph", source);
        }
        else if(!contains(destination)) {
            throw new TestGraphException("Source or destination not in graph");
        }
        boolean edgeAdded = graph.addEdge(relationship, source, destination);
        if(!edgeAdded) {
            throw new TestGraphException("Error relationship " + relationship + " between source:" + source + " and destination:" + destination);
        }
    }

    @Override
    public Set<Relationship> getInboundRelationships(XNode<?> node) {
        return ImmutableSet.copyOf(graph.getInEdges(node));
    }

    @Override
    public Set<Relationship> getOutboundRelationships(XNode<?> node) {
        return ImmutableSet.copyOf(graph.getOutEdges(node));
    }

    @Override
    public void removeRelationship(Relationship relationship) {
        graph.removeEdge(relationship);
    }
}