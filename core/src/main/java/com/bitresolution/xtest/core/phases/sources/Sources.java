package com.bitresolution.xtest.core.phases.sources;

import com.bitresolution.succor.reflection.FullyQualifiedClassName;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class Sources implements Source {

    private final Set<FullyQualifiedClassName> testClasses;

    public Sources(@NotNull Set<FullyQualifiedClassName> sources) {
        testClasses = ImmutableSet.copyOf(sources);
    }

    public int size() {
        return testClasses.size();
    }

    public boolean isEmpty() {
        return testClasses.isEmpty();
    }

    public Set<FullyQualifiedClassName> getClasses() {
        return testClasses;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(testClasses);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Sources other = (Sources) obj;
        return Objects.equal(this.testClasses, other.testClasses);
    }
}
