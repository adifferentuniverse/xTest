package com.bitresolution.xtest.core.phases.sources;

import com.bitresolution.succor.reflection.FullyQualifiedClassName;
import com.google.common.collect.ImmutableSet;

import java.util.Set;
import java.util.TreeSet;

public class Sources implements Source {

    private final Set<FullyQualifiedClassName> testClasses;

    public Sources(Set<FullyQualifiedClassName> sources) {
        testClasses = new TreeSet<FullyQualifiedClassName>(sources);
    }

    public int size() {
        return testClasses.size();
    }

    public boolean isEmpty() {
        return testClasses.isEmpty();
    }

    public Set<FullyQualifiedClassName> getClasses() {
        return ImmutableSet.copyOf(testClasses);
    }
}
