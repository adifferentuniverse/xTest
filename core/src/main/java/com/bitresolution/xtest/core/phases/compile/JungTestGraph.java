package com.bitresolution.xtest.core.phases.compile;

import com.bitresolution.xtest.core.phases.compile.nodes.BaseNode;
import com.bitresolution.xtest.core.phases.compile.nodes.Root;
import com.bitresolution.xtest.core.phases.compile.nodes.XNode;
import com.bitresolution.xtest.core.phases.compile.relationships.Relationship;
import com.google.common.base.Objects;
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
    public void addNode(XNode source) throws CompileGraphException {
        boolean nodeAdded = graph.addVertex(source);
        if(!nodeAdded) {
            throw new CompileGraphException("Error adding node:" + source);
        }
    }

    @Override
    public Set<XNode> getAdjacentNodesByRelationship(XNode<?> node, Class<? extends Relationship> relationship) {
        Collection<Relationship> edges = graph.getOutEdges(node);
        Set<XNode> nodes = new HashSet<XNode>();
        for(Relationship edge : edges) {
            if(edge.getClass().isAssignableFrom(relationship)) {
                nodes.add(graph.getDest(edge));
            }
        }
        return nodes;
    }

    @Override
    public Set<XNode> getAdjacentNodes(XNode<?> node) {
        Collection<Relationship> edges = graph.getOutEdges(node);
        Set<XNode> nodes = new HashSet<XNode>();
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
    public void addRelationship(Relationship<?, ?> relationship) throws CompileGraphException {
        addRelationship(relationship.getSource(), relationship.getDestination(), relationship);
    }

    @Override
    public void addRelationship(XNode<?> source, XNode<?> destination, Relationship relationship) throws CompileGraphException {
        if(!contains(source)) {
            throw new CompileGraphException("Source node '{}' not in graph", source);
        }
        else if(!contains(destination)) {
            throw new CompileGraphException("Source or destination not in graph");
        }
        boolean edgeAdded = graph.addEdge(relationship, source, destination);
        if(!edgeAdded) {
            throw new CompileGraphException("Error relationship " + relationship + " between source:" + source + " and destination:" + destination);
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

    @Override
    public Set<XNode> getNodes() {
        return ImmutableSet.<XNode>copyOf(this.graph.getVertices());
    }

    @Override
    public Set<Relationship> getRelationships() {
        return ImmutableSet.<Relationship>copyOf(this.graph.getEdges());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> XNode<T> findNodeByValue(T value) {
        for(XNode node : getNodes()) {
            if(node.getValue().equals(value) && node.getValue().getClass().equals(value.getClass())) {
                return node;
            }
        }
        return null;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(graph, root);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final JungTestGraph that = (JungTestGraph) obj;
        //the following is horrid but working around the fact that Jung graphs don't implement equals
        boolean verticesEqual = Objects.equal(this.getNodes(), that.getNodes());
        boolean edgesEqual = Objects.equal(this.getRelationships(), that.getRelationships());
        return verticesEqual && edgesEqual;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("graph", graph)
                .add("root", root)
                .toString();
    }
}
