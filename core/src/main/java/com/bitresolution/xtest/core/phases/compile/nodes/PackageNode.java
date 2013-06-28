package com.bitresolution.xtest.core.phases.compile.nodes;

import com.bitresolution.succor.reflection.Package;

public class PackageNode extends BaseNode<Package> implements XNode<Package> {

    public PackageNode(Package packageName) {
        super(packageName);
    }
}
