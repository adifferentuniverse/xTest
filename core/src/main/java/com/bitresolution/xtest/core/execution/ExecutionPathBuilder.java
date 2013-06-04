package com.bitresolution.xtest.core.execution;

import java.util.NavigableSet;

public interface ExecutionPathBuilder {

    NavigableSet<Fixture> parse(ExecutionTree executionTree);
}
