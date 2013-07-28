package com.bitresolution.xtest.core.lifecycle;

import com.bitresolution.succor.junit.category.Unit;
import com.bitresolution.xtest.events.Publisher;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.VerboseMockitoJUnitRunner;

import static com.bitresolution.xtest.core.events.AbortedEvent.aborted;
import static com.bitresolution.xtest.core.events.CompleteEvent.complete;
import static com.bitresolution.xtest.core.events.StartEvent.start;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(VerboseMockitoJUnitRunner.class)
@Category(Unit.class)
public class LifecycleExecutorSpec {
    
    @Mock
    private Publisher publisher;

    @Test
    public void shouldPublishLifecycleExecutionStartAndFinishEvents() throws Exception {
        //given:
        Lifecycle lifecycle = new Lifecycle();
        LifecycleExecutor executor = new LifecycleExecutor(lifecycle, publisher);

        //when:
        executor.execute();

        //then:
        verify(publisher).publish(start(executor));
        verify(publisher).publish(complete(executor));
    }

    @Test
    public void shouldExecuteLifecyclePhasesInOrder() throws Exception {
        //given:
        Lifecycle lifecycle = new Lifecycle();
        LifecycleExecutor executor = new LifecycleExecutor(lifecycle, publisher);
        MockPhase<Void, Integer> phaseA = new MockPhase<Void, Integer>(Void.class, Integer.class, 1);
        MockPhase<Integer, Integer> phaseB = new MockPhase<Integer, Integer>(Integer.class, Integer.class, 2);
        MockPhase<Integer, Void> phaseC = new MockPhase<Integer, Void>(Integer.class, Void.class, null);

        lifecycle.add(phaseA).add(phaseB).add(phaseC);

        //when:
        executor.execute();

        //then:
        verify(publisher).publish(start(executor));
        assertThat(phaseA.isExecuted(), is(true));
        assertThat(phaseB.isExecuted(), is(true));
        assertThat(phaseC.isExecuted(), is(true));
        verify(publisher).publish(complete(executor));
    }

    @Test
    public void shouldPropogateExceptionsFromLifecyclePhasess() throws Exception {
        //given:
        Lifecycle lifecycle = new Lifecycle();
        LifecycleExecutor executor = new LifecycleExecutor(lifecycle, publisher);
        MockPhase<Void, Integer> phaseA = new MockPhase<Void, Integer>(Void.class, Integer.class, 1);
        Phase<Integer, Integer> phaseB = new MockExceptionThrowingPhase<Integer, Integer>(Integer.class, Integer.class);
        MockPhase<Integer, Void> phaseC = new MockPhase<Integer, Void>(Integer.class, Void.class, null);

        lifecycle.add(phaseA).add(phaseB).add(phaseC);

        //when:
        LifecycleExecutorException exception = null;
        try {
            executor.execute();
        }
        catch (LifecycleExecutorException e) {
            exception = e;
        }

        //then:
        verify(publisher).publish(start(executor));
        verify(publisher).publish(start(phaseA));
        verify(publisher).publish(complete(phaseA));
        assertThat(phaseA.isExecuted(), is(true));
        assertThat(exception, is(instanceOf(LifecycleExecutorException.class)));
        assertThat(phaseC.isExecuted(), is(false));
        verify(publisher).publish(aborted(executor));
        verify(publisher).publish(aborted(executor));
    }

}
