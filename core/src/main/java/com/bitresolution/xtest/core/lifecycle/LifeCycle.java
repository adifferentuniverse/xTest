package com.bitresolution.xtest.core.lifecycle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Lifecycle implements Iterable<Phase<?, ?>> {

    private final List<Phase<?, ?>> phases;

    public Lifecycle() {
        phases = new ArrayList<Phase<?, ?>>();
    }

    @Override
    public Iterator<Phase<?, ?>> iterator() {
        return phases.iterator();
    }
}
