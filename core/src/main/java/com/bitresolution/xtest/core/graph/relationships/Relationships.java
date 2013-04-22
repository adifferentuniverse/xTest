package com.bitresolution.xtest.core.graph.relationships;

public class Relationships {

    public static ContainsRelationship contains() {
        return new ContainsRelationship();
    }

    private static class ContainsRelationship implements Relationship {
    }
}
