package com.bitresolution.xtest.core.graph;

import com.bitresolution.xtest.reflection.FullyQualifiedClassName;
import com.bitresolution.xtest.reflection.Package;

public interface GraphFactory {

    TestGraph from(Class<?> klass) throws TestGraphException;

    TestGraph from(FullyQualifiedClassName name) throws ClassNotFoundException, TestGraphException;

    TestGraph from(Package name) throws TestGraphException;
}
