package com.bitresolution.xtest.core.execution;

import com.bitresolution.xtest.core.XTestEvent;

public interface TestExectionListener {

    void publishEvent(XTestEvent event);
}
