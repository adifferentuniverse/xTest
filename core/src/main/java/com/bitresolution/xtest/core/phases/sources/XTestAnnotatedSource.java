package com.bitresolution.xtest.core.phases.sources;

import com.bitresolution.succor.reflection.FullyQualifiedClassName;
import com.bitresolution.succor.reflection.PackageName;
import com.bitresolution.xtest.Node;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class XTestAnnotatedSource implements Source {

    private static final Logger log = LoggerFactory.getLogger(XTestAnnotatedSource.class);

    private final PackageName packageName;

    public XTestAnnotatedSource(PackageName packageName) {
        this.packageName = packageName;
    }

    @Override
    public Set<FullyQualifiedClassName> getClasses() {
        Set<FullyQualifiedClassName> classes = new HashSet<FullyQualifiedClassName>();
        Reflections reflections = new Reflections(packageName.getName());
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(Node.class);
        for(Class<?> klass : annotatedClasses) {
            classes.add(new FullyQualifiedClassName(klass));
        }
        return classes;
    }
}
