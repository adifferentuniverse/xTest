package com.bitresolution.xtest.core.phases.sources;

import com.bitresolution.succor.reflection.FullyQualifiedClassName;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DefaultSourceBuilder implements SourceBuilder {

    private final Set<Source> includedSources;
    private final Set<Source> excludedSources;

    public DefaultSourceBuilder() {
        includedSources = new HashSet<Source>();
        excludedSources = new HashSet<Source>();
    }

    @Override
    public DefaultSourceBuilder includeClassSources(List<FullyQualifiedClassName> classNames) {
        for(FullyQualifiedClassName className : classNames) {
            includedSources.add(new ClassSource(className));
        }
        return this;
    }

    @Override
    public DefaultSourceBuilder excludeClassSources(List<FullyQualifiedClassName> classNames) {
        for(FullyQualifiedClassName className : classNames) {
            ClassSource source = new ClassSource(className);
            excludedSources.add(source);
        }
        return this;
    }

    @Override
    public Sources build() {
        return new Sources();
    }
}
