package com.bitresolution.xtest.core.phases.execute;

import com.bitresolution.xtest.core.phases.generate.Fixture;
import com.bitresolution.xtest.events.Publisher;

import java.util.concurrent.Future;

public interface FixtureInvoker {
    Future<Boolean> execute(Fixture fixture, Publisher publisher);
}
