package com.bitresolution.xtest.core.lifecycle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Lifecycle implements Iterable<Phase<?, ?>> {

    private final List<Phase<?, ?>> phases;
    private Class<?> tailType;

    public Lifecycle() {
        phases = new ArrayList<Phase<?, ?>>();
        tailType = Void.class;
    }

    public Lifecycle add(Phase<?, ?> phase) throws LifecycleExecutorException {
        if(tailType.equals(phase.getInputType())) {
            phases.add(phase);
            tailType = phase.getOutputType();
            return this;
        }
        throw new LifecycleExecutorException(
                "Phase input {} not compatible with tail value {}",
                phase.getInputType().getSimpleName(),
                tailType.getSimpleName()
        );
    }

    public Class<?> getTailType() {
        return tailType;
    }

    @Override
    public Iterator<Phase<?, ?>> iterator() {
        return phases.iterator();
    }

    public int getLength() {
        return phases.size();
    }
}
