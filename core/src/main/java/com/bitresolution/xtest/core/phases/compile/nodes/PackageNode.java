package com.bitresolution.xtest.core.phases.compile.nodes;

import com.bitresolution.succor.reflection.PackageName;

import javax.validation.constraints.NotNull;

public class PackageNode extends BaseNode<PackageName> implements XNode<PackageName> {

    public PackageNode(@NotNull PackageName packageName) {
        super(packageName);
    }
}
