package com.bitresolution.xtest.core.phases.sources;

import com.bitresolution.succor.reflection.FullyQualifiedClassName;
import com.bitresolution.succor.reflection.PackageName;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class DefaultSourceBuilder implements SourceBuilder {

    private Set<Source> includedSources;
    private Set<Source> excludedSources;

    @Override
    public DefaultSourceBuilder includeClassSources(List<FullyQualifiedClassName> classNames) {
        for(FullyQualifiedClassName className : classNames) {
            includedSources.add(new ClassSource(className));
        }
        return this;
    }

    @Override
    public SourceBuilder includePackageSources(List<String> packageNames) {
        for(String packageName : packageNames) {
            PackageSource source = new PackageSource(new PackageName(packageName));
            includedSources.add(source);
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
    public SourceBuilder excludePackageSources(List<String> packageNames) {
        for(String packageName : packageNames) {
            PackageSource source = new PackageSource(new PackageName(packageName));
            excludedSources.add(source);
        }
        return this;
    }

    @Override
    public Sources build() {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }
}
