package com.bitresolution.xtest.core.lifecycle.phase;

import com.bitresolution.xtest.core.graph.TestGraph;
import com.bitresolution.xtest.core.lifecycle.Phase;

public class CompileTestGraphPhase<I, O> implements Phase<TestSources, TestGraph> {
    @Override
    public Class<TestSources> getInputType() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Class<TestGraph> getOutputType() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public TestGraph execute(TestSources inpur) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
