package com.bitresolution.xtest.events;

import com.bitresolution.xtest.core.XTestEvent;

public interface Subscriber {
    void process(XTestEvent event);
}
