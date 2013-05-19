package com.bitresolution.xtest.core.graph.relationships;

public class Relationships {

    public static ContainsRelationship contains() {
        return new ContainsRelationship();
    }

    public static DependsOnRelationship dependsOn() {
        return new DependsOnRelationship();
    }

    public static class ContainsRelationship implements Relationship {
    }

    public static class DependsOnRelationship implements Relationship {
    }
}
