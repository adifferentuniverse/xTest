package com.bitresolution.xtest.reflection;

import com.google.common.base.Objects;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class FullyQualifiedClassName {

    private final Package packageName;
    private final String className;

    public FullyQualifiedClassName(String fullyQualifiedClassName) {
        this(parse(fullyQualifiedClassName));
    }

    private FullyQualifiedClassName(Pair<Package, String> parse) {
        this(parse.getLeft(), parse.getRight());
    }

    public FullyQualifiedClassName(Package packageName, String className) {
        this.packageName = packageName;
        this.className = className;
    }

    private static Pair<Package, String> parse(String fqcn) {
        int i = fqcn.lastIndexOf(".");
        return i == -1
               ? ImmutablePair.of(Package.DEFAULT, fqcn)
               : ImmutablePair.of(new Package(fqcn.substring(0, i)), fqcn.substring(i + 1));
    }

    public Package getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    public String getFullyQualifiedClassName() {
        return packageName == Package.DEFAULT ? className : packageName.getName() + "." + className;
    }

    public boolean isInDefaultPackage() {
        return packageName.isDefaultPackage();
    }

    public Class<?> loadClass() throws ClassNotFoundException {
        return Class.forName(getFullyQualifiedClassName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(packageName, className);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final FullyQualifiedClassName other = (FullyQualifiedClassName) obj;
        return Objects.equal(this.packageName, other.packageName) && Objects.equal(this.className, other.className);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("packageName", packageName)
                .add("className", className)
                .toString();
    }
}
