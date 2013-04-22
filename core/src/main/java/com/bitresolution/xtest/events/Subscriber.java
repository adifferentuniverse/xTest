package com.bitresolution.xtest.events;

public interface Subscriber {
    void process(XEvent event);
}
