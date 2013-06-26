package com.bitresolution.xtest.core.phases.parse.nodes;

import com.bitresolution.succor.reflection.Package;

public class PackageNode extends BaseNode<Package> implements XNode<Package> {

    public PackageNode(Package packageName) {
        super(packageName);
    }
}
