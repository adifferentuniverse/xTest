package com.bitresolution.xtest.phases.sources;

import com.bitresolution.succor.reflection.FullyQualifiedClassName;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;

import java.util.Set;

public class ClassSource implements Source {

    private final FullyQualifiedClassName className;

    public ClassSource(String className) {
        this.className = new FullyQualifiedClassName(className);
    }

    @Override
    public Set<FullyQualifiedClassName> getClasses() {
        return ImmutableSet.of(className);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(className);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ClassSource other = (ClassSource) obj;
        return Objects.equal(this.className, other.className);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("className", className)
                .toString();
    }
}
