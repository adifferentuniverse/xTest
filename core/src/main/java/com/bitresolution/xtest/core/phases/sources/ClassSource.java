package com.bitresolution.xtest.core.phases.sources;

import com.bitresolution.succor.reflection.FullyQualifiedClassName;
import com.google.common.collect.ImmutableSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class ClassSource implements Source {

    private static final Logger log = LoggerFactory.getLogger(ClassSource.class);

    private final FullyQualifiedClassName className;

    public ClassSource(FullyQualifiedClassName className) {
        this.className = className;
    }

    @Override
    public Set<FullyQualifiedClassName> getClasses() {
        return ImmutableSet.of(className);
    }
}
