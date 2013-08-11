package com.bitresolution.xtest.phases.sources;

import com.bitresolution.succor.reflection.FullyQualifiedClassName;
import com.bitresolution.succor.reflection.PackageName;
import com.bitresolution.xtest.Node;
import com.google.common.base.Objects;
import org.apache.commons.lang3.Validate;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

public class XTestAnnotatedSource implements Source {

    private final PackageName packageName;

    public XTestAnnotatedSource(@NotNull PackageName packageName) {
        Validate.notNull(packageName, "package name can not be null");
        this.packageName = packageName;
    }

    @Override
    @NotNull
    public Set<FullyQualifiedClassName> getClasses() {
        Set<FullyQualifiedClassName> classes = new HashSet<FullyQualifiedClassName>();
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .addUrls(ClasspathHelper.forPackage(packageName.getName()))
                .filterInputsBy(new FilterBuilder().exclude(".*.jnilib")) //can't scan this file type
        );
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
