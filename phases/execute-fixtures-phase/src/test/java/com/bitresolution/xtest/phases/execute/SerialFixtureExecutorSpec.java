package com.bitresolution.xtest.phases.execute;

import com.bitresolution.succor.junit.category.Unit;
import com.bitresolution.xtest.events.Publisher;
import com.bitresolution.xtest.phases.generate.Fixture;
import com.bitresolution.xtest.phases.generate.Fixtures;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.VerboseMockitoJUnitRunner;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static com.bitresolution.xtest.core.events.CompleteEvent.complete;
import static com.bitresolution.xtest.core.events.QueuedEvent.queued;
import static com.bitresolution.xtest.core.events.StartEvent.start;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(VerboseMockitoJUnitRunner.class)
@Category(Unit.class)
public class SerialFixtureExecutorSpec {

    @Mock
    Publisher publisher;
    @Mock
    FixtureInvoker fixtureInvoker;

    private FixtureExecutor fixtureExecutor;

    @Before
    public void setup() {
        fixtureExecutor = new SerialFixtureExecutor(fixtureInvoker, publisher);
    }

    @Test
    public void shouldExecuteEmptyTreeSuccessfully() {
        //given
        Fixtures fixtures = new Fixtures();

        //when:
        fixtureExecutor.execute(fixtures);

        //then:
        InOrder order = inOrder(publisher);
        order.verify(publisher).publish(start(fixtureExecutor));
        order.verify(publisher).publish(complete(fixtureExecutor));
        verifyNoMoreInteractions(publisher);
        verifyNoMoreInteractions(fixtureInvoker);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldExecuteSingleFixtureTreeSuccessfully() {
        //given:
        Fixtures fixtures = new Fixtures();
        Fixture fixture = mock(Fixture.class);
        fixtures.add(fixture);

        Future<Boolean> future = mock(Future.class);
        given(fixtureInvoker.execute(fixture, publisher)).willReturn(future);

        //when:
        fixtureExecutor.execute(fixtures);

        //then:
        InOrder order = inOrder(publisher, fixtureInvoker);
        order.verify(publisher).publish(start(fixtureExecutor));
        order.verify(publisher).publish(queued(fixture));
        order.verify(fixtureInvoker).execute(fixture, publisher);
        order.verify(publisher).publish(complete(fixtureExecutor));
        verifyNoMoreInteractions(fixtureInvoker, publisher);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldExecuteFixturesSequentially() {
        //given:
        Fixtures fixtures = new Fixtures();
        MockFixtureFactory fixtureFactory = new MockFixtureFactory();
        Fixture fixture0 = fixtureFactory.create();
        Fixture fixture1 = fixtureFactory.create();
        Fixture fixture2 = fixtureFactory.create();
        fixtures.add(fixture0);
        fixtures.add(fixture1);
        fixtures.add(fixture2);

        Future<Boolean> future = mock(Future.class);
        given(fixtureInvoker.execute(any(Fixture.class), eq(publisher))).willReturn(future);

        //when:
        fixtureExecutor.execute(fixtures);

        //then:
        InOrder order = inOrder(publisher, fixtureInvoker);
        order.verify(publisher).publish(start(fixtureExecutor));
        order.verify(publisher).publish(queued(fixture0));
        order.verify(fixtureInvoker).execute(fixture0, publisher);
        order.verify(publisher).publish(queued(fixture1));
        order.verify(fixtureInvoker).execute(fixture1, publisher);
        order.verify(publisher).publish(queued(fixture2));
        order.verify(fixtureInvoker).execute(fixture2, publisher);
        order.verify(publisher).publish(complete(fixtureExecutor));
        verifyNoMoreInteractions(fixtureInvoker, publisher);
    }

    private static class MockFixture extends Fixture {
        public MockFixture(Integer index) {
            super(index);
        }
    }

    private static class MockFixtureFactory {
        private final AtomicInteger count = new AtomicInteger(0);

        public MockFixture create() {
            return new MockFixture(count.getAndIncrement());
        }
    }
}
