package com.bitresolution.xtest.core.events;

import com.bitresolution.xtest.events.XEvent;
import com.google.common.base.Objects;

public abstract class BaseEvent implements XEvent {

    private final Object source;

    protected BaseEvent(Object source) {
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
        final BaseEvent other = (BaseEvent) obj;
        return Objects.equal(this.source, other.source);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("source", source)
                .toString();
    }
}
