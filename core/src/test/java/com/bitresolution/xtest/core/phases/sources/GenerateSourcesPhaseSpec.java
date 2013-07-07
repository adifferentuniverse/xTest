package com.bitresolution.xtest.core.phases.sources;

import com.bitresolution.xtest.core.XTestConfiguration;
import com.bitresolution.xtest.events.Publisher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.VerboseMockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(VerboseMockitoJUnitRunner.class)
public class GenerateSourcesPhaseSpec {

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
        GenerateSourcesPhase phase = new GenerateSourcesPhase(publisher, builder, configuration);

        //then
        assertThat(phase.getInputType(), equalTo(Void.class));
    }

    @Test
    public void shouldHaveOutputTypeOfSources() {
        //when
        GenerateSourcesPhase phase = new GenerateSourcesPhase(publisher, builder, configuration);

        //then
        assertThat(phase.getOutputType(), equalTo(Sources.class));
    }

    @Test
    public void shouldBuildSources() throws Exception {
        //given
        Sources sources = mock(Sources.class);
        given(builder.includeClassSources(anyList())).willReturn(builder);
        given(builder.excludeClassSources(anyList())).willReturn(builder);
        given(builder.build()).willReturn(sources);

        GenerateSourcesPhase phase = new GenerateSourcesPhase(publisher, builder, configuration);

        //when
        Sources output = phase.execute(null);

        //then
        verify(builder).includeClassSources(anyList());
        verify(builder).excludeClassSources(anyList());
        assert output.equals(sources);
    }
}
