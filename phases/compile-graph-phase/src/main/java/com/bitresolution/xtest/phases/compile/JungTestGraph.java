package com.bitresolution.xtest.phases.compile;

import com.bitresolution.xtest.phases.compile.nodes.GenericNode;
import com.bitresolution.xtest.phases.compile.nodes.Root;
import com.bitresolution.xtest.phases.compile.nodes.XNode;
import com.bitresolution.xtest.phases.compile.relationships.Relationship;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class JungTestGraph implements TestGraph {

    @NotNull
    private final Graph<XNode<?>, Relationship<?, ?>> graph;
    @NotNull
    private final XNode<Root> root;

    public JungTestGraph() {
        this(new DirectedSparseMultigraph<XNode<?>, Relationship<?, ?>>(), new GenericNode<Root>(Root.ROOT));
    }

    public JungTestGraph(@NotNull Graph<XNode<?>, Relationship<?, ?>> graph, @NotNull XNode<Root> root) {
        this.graph = graph;
        this.root = root;
        graph.addVertex(root);
    }

    @Override
    @NotNull
    public XNode<?> getRootNode() {
        return root;
    }

    @Override
    public void addNode(@NotNull XNode<?> source) throws CompileGraphException {
        boolean nodeAdded = graph.addVertex(source);
        if(!nodeAdded) {
            throw new CompileGraphException("Error adding node:" + source);
        }
    }

    @Override
    @NotNull
    public Set<XNode<?>> getAdjacentNodesByRelationship(@NotNull XNode<?> node, @NotNull Class<? extends Relationship<?, ?>> relationship) {
        Collection<Relationship<?, ?>> edges = graph.getOutEdges(node);
        Set<XNode<?>> nodes = new HashSet<XNode<?>>();
        for(Relationship<?, ?> edge : edges) {
            if(edge.getClass().isAssignableFrom(relationship)) {
                nodes.add(graph.getDest(edge));
            }
        }
        return nodes;
    }

    @Override
    @NotNull
    public Set<XNode<?>> getAdjacentNodes(@NotNull XNode<?> node) {
        Collection<Relationship<?, ?>> edges = graph.getOutEdges(node);
        Set<XNode<?>> nodes = new HashSet<XNode<?>>();
        for(Relationship<?, ?> edge : edges) {
            nodes.add(graph.getDest(edge));
        }
        return nodes;
    }

    @Override
    public boolean contains(@NotNull XNode<?> node) {
        return graph.containsVertex(node);
    }

    @Override
    public boolean contains(@NotNull Relationship<?, ?> relationship) {
        return graph.containsEdge(relationship);
    }

    @Override
    public void addRelationship(@NotNull Relationship<?, ?> relationship) throws CompileGraphException {
        XNode<?> source = relationship.getSource();
        XNode<?> destination = relationship.getDestination();
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
    @NotNull
    public Set<Relationship<?, ?>> getInboundRelationships(@NotNull XNode<?> node) {
        return ImmutableSet.copyOf(graph.getInEdges(node));
    }

    @Override
    @NotNull
    public Set<Relationship<?, ?>> getOutboundRelationships(@NotNull XNode<?> node) {
        return ImmutableSet.copyOf(graph.getOutEdges(node));
    }

    @Override
    public void removeRelationship(@NotNull Relationship<?, ?> relationship) {
        graph.removeEdge(relationship);
    }

    @Override
    @NotNull
    public Set<XNode<?>> getNodes() {
        return ImmutableSet.copyOf(this.graph.getVertices());
    }

    @Override
    @NotNull
    public Set<Relationship<?, ?>> getRelationships() {
        return ImmutableSet.copyOf(this.graph.getEdges());
    }

    @Override
    @SuppressWarnings("unchecked")
    @NotNull
    public <T> Optional<XNode<T>> findNodeByValue(@NotNull T value) {
        for(XNode<?> node : getNodes()) {
            if(node.getValue().equals(value) && node.getValue().getClass().equals(value.getClass())) {
                return Optional.of((XNode<T>)node);
            }
        }
        return Optional.absent();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getNodes(), getRelationships());
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
                .add("nodes", getNodes())
                .add("relationships", getRelationships())
                .toString();
    }
}
