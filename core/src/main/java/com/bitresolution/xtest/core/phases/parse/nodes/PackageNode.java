package com.bitresolution.xtest.core.phases.parse.nodes;

import com.bitresolution.xtest.commons.reflection.Package;

public class PackageNode extends BaseNode<Package> implements XNode<Package> {

    public PackageNode(Package packageName) {
        super(packageName);
    }
}