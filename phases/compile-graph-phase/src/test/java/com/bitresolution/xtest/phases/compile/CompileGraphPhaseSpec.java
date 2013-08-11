package com.bitresolution.xtest.phases.compile;

import com.bitresolution.succor.junit.category.Unit;
import com.bitresolution.succor.reflection.FullyQualifiedClassName;
import com.bitresolution.xtest.core.lifecycle.LifecycleExecutorException;
import com.bitresolution.xtest.eventbus.Publisher;
import com.bitresolution.xtest.phases.sources.Sources;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.VerboseMockitoJUnitRunner;

import java.util.Collections;

import static com.bitresolution.xtest.events.CompleteEvent.complete;
import static com.bitresolution.xtest.events.StartEvent.start;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(VerboseMockitoJUnitRunner.class)
@org.junit.experimental.categories.Category(Unit.class)
public class CompileGraphPhaseSpec {

    @Mock
    private GraphBuilder builder;
    @Mock
    private Publisher publisher;

    private CompileGraphPhase phase;

    @Before
    public void setUp() throws Exception {
        phase = new CompileGraphPhase(publisher, builder);
    }

    @Test
    public void shouldHaveInputTypeOfVoid() {
        assertThat(phase.getInputType(), equalTo(Sources.class));
    }

    @Test
    public void shouldHaveOutputTypeOfSources() {
        //then
        assertThat(phase.getOutputType(), equalTo(TestGraph.class));
    }

    @Test
    public void shouldBuildGraph() throws Exception {
        //given
        Sources input = new Sources(Collections.<FullyQualifiedClassName>emptySet());
        given(builder.add(input)).willReturn(builder);
        given(builder.build()).willReturn(new JungTestGraph());

        //when
        TestGraph output = phase.execute(input);

        //then
        assertThat(output, is((TestGraph) new JungTestGraph()));
        verify(publisher).publish(start(phase));
        verify(builder).add(input);
        verify(builder).build();
        verify(publisher).publish(complete(phase));
    }

    @Test(expected = LifecycleExecutorException.class)
    public void shouldTranslateExceptionsThrownDuringCompilations() throws Exception {
        //given
        Sources input = new Sources(Collections.<FullyQualifiedClassName>emptySet());
        given(builder.add(input)).willReturn(builder);
        given(builder.build()).willThrow(new IllegalArgumentException());

        //when
        phase.execute(input);

        //then exception thrown
    }

    @Test
    public void shouldHonorEqualsContract() {
        EqualsVerifier.forClass(CompileGraphPhase.class)
                .usingGetClass()
                .verify();

    }
}
