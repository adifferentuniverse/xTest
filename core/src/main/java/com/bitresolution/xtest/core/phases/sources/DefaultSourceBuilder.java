package com.bitresolution.xtest.core.phases.sources;

import com.bitresolution.succor.reflection.FullyQualifiedClassName;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DefaultSourceBuilder implements SourceBuilder {

    private final Set<FullyQualifiedClassName> includedSources;
    private final Set<FullyQualifiedClassName> excludedSources;

    public DefaultSourceBuilder() {
        includedSources = new HashSet<FullyQualifiedClassName>();
        excludedSources = new HashSet<FullyQualifiedClassName>();
    }

    @Override
    public DefaultSourceBuilder includeClassSources(List<ClassSource> sources) {
        for(ClassSource className : sources) {
            includedSources.addAll(className.getClasses());
        }
        return this;
    }

    @Override
    public DefaultSourceBuilder excludeClassSources(List<ClassSource> sources) {
        for(ClassSource source : sources) {
            excludedSources.addAll(source.getClasses());
        }
        return this;
    }

    @Override
    public Sources build() {
        HashSet<FullyQualifiedClassName> classes = new HashSet<FullyQualifiedClassName>();
        classes.addAll(includedSources);
        return new Sources(classes);
    }
}
