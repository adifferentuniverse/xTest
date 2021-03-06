package com.bitresolution.xtest.phases.execute;

import com.bitresolution.xtest.eventbus.Publisher;
import com.bitresolution.xtest.phases.generate.Fixture;
import com.bitresolution.xtest.phases.generate.Fixtures;

import java.util.concurrent.Future;

import static com.bitresolution.xtest.events.CompleteEvent.complete;
import static com.bitresolution.xtest.events.QueuedEvent.queued;
import static com.bitresolution.xtest.events.StartEvent.start;

public class SerialFixtureExecutor implements FixtureExecutor {

    private final FixtureInvoker fixtureInvoker;
    private final Publisher publisher;

    public SerialFixtureExecutor(FixtureInvoker fixtureInvoker, Publisher publisher) {
        this.fixtureInvoker = fixtureInvoker;
        this.publisher = publisher;
    }

    @Override
    public void execute(Fixtures fixtures) {
        publisher.publish(start(this));
        for(Fixture fixture : fixtures) {
            execute(fixture);
        }
        publisher.publish(complete(this));
    }

    private Future<Boolean> execute(Fixture fixture) {
        publisher.publish(queued(fixture));
        return fixtureInvoker.execute(fixture, publisher);
    }
}
