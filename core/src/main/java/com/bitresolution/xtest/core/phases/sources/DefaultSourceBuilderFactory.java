package com.bitresolution.xtest.core.phases.sources;

import org.springframework.stereotype.Component;

@Component
public class DefaultSourceBuilderFactory implements SourceBuilderFactory {

    @Override
    public SourceBuilder create() {
        return new SourceBuilder();
    }
}
