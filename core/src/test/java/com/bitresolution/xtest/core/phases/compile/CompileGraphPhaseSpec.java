package com.bitresolution.xtest.core.phases.compile;

import com.bitresolution.succor.reflection.FullyQualifiedClassName;
import com.bitresolution.xtest.core.XTestConfiguration;
import com.bitresolution.xtest.core.phases.sources.SourceBuilder;
import com.bitresolution.xtest.core.phases.sources.Sources;
import com.bitresolution.xtest.events.Publisher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.VerboseMockitoJUnitRunner;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(VerboseMockitoJUnitRunner.class)
public class CompileGraphPhaseSpec {

    @Mock
    private SourceBuilder builder;
    @Mock
    private Publisher publisher;

    private XTestConfiguration configuration;

    @Before
    public void setUp() throws Exception {
        configuration = new XTestConfiguration();
    }

    @Test
    public void shouldHaveInputTypeOfVoid() {
        //when
        CompileGraphPhase phase = new CompileGraphPhase(publisher);

        //then
        assertThat(phase.getInputType(), equalTo(Sources.class));
    }

    @Test
    public void shouldHaveOutputTypeOfSources() {
        //when
        CompileGraphPhase phase = new CompileGraphPhase(publisher);

        //then
        assertThat(phase.getOutputType(), equalTo(TestGraph.class));
    }

    @Test
    public void shouldBuildGraph() throws Exception {
        //given
        CompileGraphPhase phase = new CompileGraphPhase(publisher);
        Sources input = new Sources(Collections.<FullyQualifiedClassName>emptySet());

        //when
        TestGraph output = phase.execute(input);

        //then
        TestGraph expectedGraph = new JungTestGraph();
        assertThat(output, equalTo(expectedGraph));
    }
}
