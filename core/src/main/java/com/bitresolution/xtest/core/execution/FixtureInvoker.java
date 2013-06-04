package com.bitresolution.xtest.core.execution;

import com.bitresolution.xtest.events.Publisher;

import java.util.concurrent.Future;

public interface FixtureInvoker {
    Future<Boolean> execute(Fixture fixture, Publisher<TestExectionListener> publisher);
}
