package com.bitresolution.xtest.core.lifecycle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Lifecycle implements Iterable<Phase<?, ?>> {

    private final List<Phase<?, ?>> phases;

    public Lifecycle() {
        phases = new ArrayList<Phase<?, ?>>();
    }

    public Lifecycle add(Phase<?, ?> phase) {
        phases.add(phase);
        return this;
    }

    @Override
    public Iterator<Phase<?, ?>> iterator() {
        return phases.iterator();
    }
}
