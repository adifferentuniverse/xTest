package com.bitresolution.xtest.events;

import com.bitresolution.xtest.core.execution.TestExectionListener;

public interface XEventSource {
    Publisher<TestExectionListener> getPublisher();
}
