package com.bitresolution.xtest.commons.reflection;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;

public class Package {

    public static final Package DEFAULT = new Package();

    private static final Joiner joiner = Joiner.on(".");

    private final String[] parts;

    public Package() {
        this(new String[0]);
    }

    public Package(String packageName) {
        this(parse(packageName));
    }

    public Package(String[] parts) {
        this.parts = parts;
    }

    private static String[] parse(String packageName) {
        if(Strings.isNullOrEmpty(packageName))
            return DEFAULT.parts;
        else
            return packageName.lastIndexOf(".") == -1 ? new String[]{packageName} : packageName.split("\\.");
    }

    public boolean isDefaultPackage() {
        return parts.length == 0;
    }

    public Package getParent() {
        return new Package(Arrays.copyOfRange(parts, 0, parts.length == 0 ? 0 : parts.length - 1));
    }

    public Package getChild(String name) {
        return new Package(ArrayUtils.add(parts, name));
    }

    public String[] getParts() {
        return parts;
    }

    public String getName() {
        return joiner.join(parts);
    }

    @Override
    @SuppressWarnings("vararg")
    public int hashCode() {
        return Arrays.hashCode(parts);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Package other = (Package) obj;
        return Arrays.equals(this.parts, other.parts);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("parts", parts)
                .toString();
    }
}
