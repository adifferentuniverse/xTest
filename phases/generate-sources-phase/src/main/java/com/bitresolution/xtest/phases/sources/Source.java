package com.bitresolution.xtest.phases.sources;

import com.bitresolution.succor.reflection.FullyQualifiedClassName;

import java.util.Set;

public interface Source {

    Set<FullyQualifiedClassName> getClasses();
}
