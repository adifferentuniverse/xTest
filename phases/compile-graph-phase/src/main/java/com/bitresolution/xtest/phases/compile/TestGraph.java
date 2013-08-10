package com.bitresolution.xtest.phases.compile;

import com.bitresolution.xtest.phases.compile.nodes.XNode;
import com.bitresolution.xtest.phases.compile.relationships.Relationship;
import com.google.common.base.Optional;

import javax.validation.constraints.NotNull;
import java.util.Set;

public interface TestGraph {

    @NotNull
    XNode<?> getRootNode();

    @NotNull
    Set<XNode<?>> getAdjacentNodesByRelationship(@NotNull XNode<?> node, @NotNull Class<? extends Relationship<?, ?>> relationship);

    @NotNull
    Set<XNode<?>> getAdjacentNodes(@NotNull XNode<?> node);

    boolean contains(@NotNull XNode<?> node);

    void addNode(@NotNull XNode<?> source) throws CompileGraphException;

    boolean contains(@NotNull Relationship<?, ?> relationship);

    @NotNull
    Set<Relationship<?, ?>> getInboundRelationships(@NotNull XNode<?> node);

    @NotNull
    Set<Relationship<?, ?>> getOutboundRelationships(@NotNull XNode<?> node);

    void removeRelationship(@NotNull Relationship<?, ?> p);

    @NotNull
    Set<XNode<?>> getNodes();

    @NotNull
    Set<Relationship<?, ?>> getRelationships();

    @NotNull
    <T> Optional<XNode<T>> findNodeByValue(@NotNull T packageName);

    void addRelationship(@NotNull Relationship<?, ?> relationship) throws CompileGraphException;
}
