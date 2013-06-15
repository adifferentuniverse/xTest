package com.bitresolution.xtest.core.lifecycle.phase;

import com.bitresolution.xtest.core.lifecycle.Phase;

public class ProcessSourcesPhase<I, O> implements Phase<Void, TestSources> {
    @Override
    public Class<Void> getInputType() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Class<TestSources> getOutputType() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public TestSources execute(Void inpur) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
