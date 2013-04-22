package com.bitresolution.xtest.core.graph;

import com.bitresolution.xtest.core.graph.nodes.BaseNode;
import com.bitresolution.xtest.core.graph.nodes.Node;
import com.bitresolution.xtest.core.graph.nodes.Root;
import com.bitresolution.xtest.core.graph.relationships.Relationship;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class JungTestGraph implements TestGraph {

    private final Graph<Node, Relationship> graph;
    private final Node<Root> root;

    public JungTestGraph() {
        graph = new DirectedSparseMultigraph<Node, Relationship>();
        root = new BaseNode<Root>(Root.ROOT, this);
        graph.addVertex(root);
    }

    @Override
    public Node getRootNode() {
        return root;
    }

    @Override
    public void addNode(Node source, Node destination, Relationship relationship) throws TestGraphException {
        boolean nodeAdded = graph.addVertex(destination);
        if(!nodeAdded) {
            throw new TestGraphException("Error adding node:" + destination);
        }
        boolean edgeAdded = graph.addEdge(relationship, source, destination);
        if(!edgeAdded) {
            graph.removeVertex(destination);
            throw new TestGraphException("Error adding node:" + destination);
        }
    }

    @Override
    public Set<Node<?>> getAdjacentNodesByRelationship(Node<?> node, Relationship relationship) {
        Collection<Relationship> edges = graph.getOutEdges(node);
        Set<Node<?>> nodes = new HashSet<Node<?>>();
        for(Relationship edge : edges) {
            if(edge.getClass().isInstance(relationship)) {
                nodes.add(graph.getDest(edge));
            }
        }
        return nodes;
    }

    @Override
    public Set<Node<?>> getAdjacentNodesp(Node<?> node) {
        Collection<Relationship> edges = graph.getOutEdges(node);
        Set<Node<?>> nodes = new HashSet<Node<?>>();
        for(Relationship edge : edges) {
            nodes.add(graph.getDest(edge));
        }
        return nodes;
    }

    @Override
    public boolean contains(Node<?> node) {
        return graph.containsVertex(node);
    }

    @Override
    public void addRelationship(Node<?> source, Node<?> destination, Relationship relationship) throws TestGraphException {
        boolean edgeAdded = graph.addEdge(relationship, source, destination);
        if(!edgeAdded) {
            throw new TestGraphException("Error relationship " + relationship + " between source:" + source + " and destination:" + destination);
        }
    }

}
