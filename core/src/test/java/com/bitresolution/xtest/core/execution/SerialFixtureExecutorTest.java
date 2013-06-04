package com.bitresolution.xtest.core.execution;

import com.bitresolution.xtest.events.Publisher;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.VerboseMockitoJUnitRunner;

import java.util.TreeSet;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static com.bitresolution.TestCategories.Unit;
import static com.bitresolution.xtest.core.execution.events.CompleteEvent.complete;
import static com.bitresolution.xtest.core.execution.events.QueuedEvent.queued;
import static com.bitresolution.xtest.core.execution.events.StartEvent.start;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(VerboseMockitoJUnitRunner.class)
@Category(Unit.class)
public class SerialFixtureExecutorTest {

    @Mock
    ExecutionTree tree;
    @Mock
    Publisher<TestExectionListener> publisher;
    @Mock
    ExecutionPathBuilderFactory builderFactory;
    @Mock
    FixtureInvoker fixtureInvoker;
    @Mock
    ExecutionPathBuilder builder;

    @Test
    public void shouldExecuteEmptyTreeSuccessfully() {
        //given:
        given(builderFactory.getBuilder()).willReturn(builder);
        given(builder.parse(tree)).willReturn(new TreeSet<Fixture>());

        FixtureExecutor testExecutor = new SerialFixtureExecutor(builderFactory, fixtureInvoker, publisher);

        //when:
        testExecutor.execute(tree);

        //then:
        InOrder order = inOrder(publisher);
        order.verify(publisher).publish(start(testExecutor));
        order.verify(publisher).publish(complete(testExecutor));
        verifyNoMoreInteractions(publisher);
        verifyNoMoreInteractions(fixtureInvoker);
    }

    @Test
    public void shouldExecuteSingleFixtureTreeSuccessfully() {
        //given:
        TreeSet<Fixture> fixtures = new TreeSet<Fixture>();
        Fixture fixture = mock(Fixture.class);
        fixtures.add(fixture);

        Future<Boolean> future = mock(Future.class);

        given(builderFactory.getBuilder()).willReturn(builder);
        given(builder.parse(tree)).willReturn(fixtures);
        given(fixtureInvoker.execute(fixture, publisher)).willReturn(future);

        FixtureExecutor testExecutor = new SerialFixtureExecutor(builderFactory, fixtureInvoker, publisher);

        //when:
        testExecutor.execute(tree);

        //then:
        InOrder order = inOrder(publisher, fixtureInvoker);
        order.verify(publisher).publish(start(testExecutor));
        order.verify(publisher).publish(queued(fixture));
        order.verify(fixtureInvoker).execute(fixture, publisher);
        order.verify(publisher).publish(complete(testExecutor));
        verifyNoMoreInteractions(fixtureInvoker, publisher);
    }

    @Test
    public void shouldExecuteFixturesSequentially() {
        //given:
        TreeSet<Fixture> fixtures = new TreeSet<Fixture>();
        MockFixtureFactory fixtureFactory = new MockFixtureFactory();
        Fixture fixture0 = fixtureFactory.create();
        Fixture fixture1 = fixtureFactory.create();
        Fixture fixture2 = fixtureFactory.create();
        fixtures.add(fixture0);
        fixtures.add(fixture1);
        fixtures.add(fixture2);

        Future<Boolean> future = mock(Future.class);

        given(builderFactory.getBuilder()).willReturn(builder);
        given(builder.parse(tree)).willReturn(fixtures);
        given(fixtureInvoker.execute(any(Fixture.class), eq(publisher))).willReturn(future);

        FixtureExecutor testExecutor = new SerialFixtureExecutor(builderFactory, fixtureInvoker, publisher);

        //when:
        testExecutor.execute(tree);

        //then:
        InOrder order = inOrder(publisher, fixtureInvoker);
        order.verify(publisher).publish(start(testExecutor));
        order.verify(publisher).publish(queued(fixture0));
        order.verify(fixtureInvoker).execute(fixture0, publisher);
        order.verify(publisher).publish(queued(fixture1));
        order.verify(fixtureInvoker).execute(fixture1, publisher);
        order.verify(publisher).publish(queued(fixture2));
        order.verify(fixtureInvoker).execute(fixture2, publisher);
        order.verify(publisher).publish(complete(testExecutor));
        verifyNoMoreInteractions(fixtureInvoker, publisher);
    }

    private static class MockFixture implements Fixture, Comparable<MockFixture> {

        private final Integer index;

        private MockFixture(Integer index) {
            this.index = index;
        }

        @Override
        public int compareTo(MockFixture that) {
            return this.index.compareTo(that.index);
        }
    }

    private static class MockFixtureFactory {
        private AtomicInteger count = new AtomicInteger(0);

        public MockFixture create() {
            return new MockFixture(count.getAndIncrement());
        }
    }
}
