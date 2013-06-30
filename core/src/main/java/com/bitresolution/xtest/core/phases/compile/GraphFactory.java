package com.bitresolution.xtest.core.phases.compile;

import com.bitresolution.succor.reflection.FullyQualifiedClassName;
import com.bitresolution.succor.reflection.PackageName;

public interface GraphFactory {

    TestGraph from(Class<?> klass) throws TestGraphException;

    TestGraph from(FullyQualifiedClassName name) throws ClassNotFoundException, TestGraphException;

    TestGraph from(PackageName name) throws TestGraphException;
}
