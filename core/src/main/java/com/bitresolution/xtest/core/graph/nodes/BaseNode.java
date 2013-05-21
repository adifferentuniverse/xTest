package com.bitresolution.xtest.core.graph.nodes;

import com.bitresolution.xtest.core.graph.TestGraph;
import com.bitresolution.xtest.core.graph.TestGraphException;
import com.bitresolution.xtest.core.graph.relationships.Relationship;
import com.google.common.base.Objects;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Set;

public class BaseNode<T> implements XNode<T> {

    private final T value;
    private final TestGraph testGraph;

    public BaseNode(T value, TestGraph testGraph) {
        this.value = value;
        this.testGraph = testGraph;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void addNode(XNode node, Relationship relationship) throws TestGraphException {
        if(testGraph.contains(node)) {
            testGraph.addRelationship(this, node, relationship);
        }
        else {
            testGraph.addNode(this, node, relationship);
        }
    }

    @Override
    public Set<XNode<?>> getAdjacentNodesByRelationship(Class<? extends Relationship> relationship) {
        return testGraph.getAdjacentNodesByRelationship(this, relationship);
    }

    @Override
    public Set<XNode<?>> getAdjacentNodes() {
        return testGraph.getAdjacentNodes(this);
    }

    @Override
    public TestGraph getTestGraph() {
        return testGraph;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value, testGraph);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("value", value)
                .append("testGraph", testGraph.hashCode())
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final BaseNode other = (BaseNode) obj;
        return Objects.equal(this.value, other.value) && Objects.equal(this.testGraph, other.testGraph);
    }
}
