package com.bitresolution.xtest.core.graph.nodes;

import com.bitresolution.xtest.core.graph.TestGraph;
import com.bitresolution.xtest.reflection.Package;

public class PackageNode extends BaseNode<Package> implements XNode<Package> {

    public PackageNode(Package packageName, TestGraph testGraph) {
        super(packageName, testGraph);
    }
}
