package com.bitresolution.xtest.core.execution;

import com.bitresolution.xtest.core.TestGraph;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class GraphTestExecutor implements TestExecutor {

    private Set<TestExectionListener> listeners;

    public GraphTestExecutor() {
        listeners = new CopyOnWriteArraySet<TestExectionListener>();
    }

    public void execute(TestGraph p) {
        notifyListeners(TestExecutorEvent.start(this));
        notifyListeners(TestExecutorEvent.complete(this));
    }

    private void notifyListeners(TestExecutorEvent event) {
        for(TestExectionListener l : listeners) {
            l.publishEvent(event);
        }
    }

    @Override
    public void addListener(TestExectionListener listener) {
        listeners.add(listener);
    }
}
