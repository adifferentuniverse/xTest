package com.bitresolution.xtest.phases.execute;

import com.bitresolution.xtest.phases.generate.Fixture;
import com.bitresolution.xtest.events.Publisher;

import java.util.concurrent.Future;

public interface FixtureInvoker {
    Future<Boolean> execute(Fixture fixture, Publisher publisher);
}
