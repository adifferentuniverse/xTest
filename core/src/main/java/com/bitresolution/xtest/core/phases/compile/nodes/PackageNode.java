package com.bitresolution.xtest.core.phases.compile.nodes;

import com.bitresolution.succor.reflection.PackageName;

public class PackageNode extends BaseNode<PackageName> implements XNode<PackageName> {

    public PackageNode(PackageName packageName) {
        super(packageName);
    }
}
