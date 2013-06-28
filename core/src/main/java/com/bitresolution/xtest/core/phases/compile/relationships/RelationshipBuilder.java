package com.bitresolution.xtest.core.phases.compile.relationships;

import com.bitresolution.xtest.core.phases.compile.nodes.XNode;

public class RelationshipBuilder<S> {

    private final XNode<S> source;

    public RelationshipBuilder(XNode<S> source) {
        this.source = source;
    }

    public static <S> RelationshipBuilder<S> where(XNode<S> source) {
        return new RelationshipBuilder<S>(source);
    }

    public <D> ContainsRelationship<S, D> contains(XNode<D> destination) {
        return new ContainsRelationship<S, D>(source, destination);
    }

    public <D> DependsOnRelationship<S, D> dependsOn(XNode<D> destination) {
        return new DependsOnRelationship<S, D>(source, destination);
    }

}
