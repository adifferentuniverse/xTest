package com.bitresolution.xtest.core.phases.sources;

import com.bitresolution.succor.reflection.FullyQualifiedClassName;
import com.bitresolution.succor.reflection.PackageName;
import com.bitresolution.xtest.Node;
import com.google.common.base.Objects;
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

    @Override
    public int hashCode() {
        return Objects.hashCode(packageName);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final XTestAnnotatedSource other = (XTestAnnotatedSource) obj;
        return Objects.equal(this.packageName, other.packageName);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("packageName", packageName)
                .toString();
    }
}
