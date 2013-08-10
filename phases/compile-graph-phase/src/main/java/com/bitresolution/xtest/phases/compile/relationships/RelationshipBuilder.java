package com.bitresolution.xtest.phases.compile.relationships;

import com.bitresolution.xtest.phases.compile.nodes.XNode;

import javax.validation.constraints.NotNull;

public class RelationshipBuilder<S> {

    private final XNode<S> source;

    public RelationshipBuilder(@NotNull XNode<S> source) {
        this.source = source;
    }

    public static <S> RelationshipBuilder<S> where(@NotNull XNode<S> source) {
        return new RelationshipBuilder<S>(source);
    }

    public <D> ContainsRelationship<S, D> contains(@NotNull XNode<D> destination) {
        return new ContainsRelationship<S, D>(source, destination);
    }

    public <D> DependsOnRelationship<S, D> dependsOn(@NotNull XNode<D> destination) {
        return new DependsOnRelationship<S, D>(source, destination);
    }

}
