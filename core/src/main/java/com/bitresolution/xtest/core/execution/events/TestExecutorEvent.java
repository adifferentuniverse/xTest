package com.bitresolution.xtest.core.execution.events;

import com.bitresolution.xtest.events.XEvent;
import com.google.common.base.Objects;

public abstract class TestExecutorEvent implements XEvent {

    private final Object source;

    public TestExecutorEvent(Object source) {
        this.source = source;
    }

    @Override
    public Object getSource() {
        return source;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(source);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final TestExecutorEvent other = (TestExecutorEvent) obj;
        return Objects.equal(this.source, other.source);
    }

    public static class Start extends TestExecutorEvent {
        public Start(Object source) {
            super(source);
        }
    }

    public static class Complete extends TestExecutorEvent {
        public Complete(Object source) {
            super(source);
        }
    }

    public static class Aborted extends TestExecutorEvent {
        public Aborted(Object source) {
            super(source);
        }
    }

    public static Start start(Object source) {
        return new Start(source);
    }

    public static Complete complete(Object source) {
        return new Complete(source);
    }

    public static Aborted aborted(Object source) {
        return new Aborted(source);
    }
}
