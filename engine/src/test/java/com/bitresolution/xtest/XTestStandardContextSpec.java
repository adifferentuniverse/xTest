package com.bitresolution.xtest;

import com.bitresolution.succor.junit.category.Unit;
import com.bitresolution.xtest.core.lifecycle.LifecycleExecutorException;
import com.bitresolution.xtest.core.lifecycle.Phase;
import com.bitresolution.xtest.events.Publisher;
import com.bitresolution.xtest.phases.compile.CompileGraphPhase;
import com.bitresolution.xtest.phases.compile.GraphBuilder;
import com.bitresolution.xtest.phases.execute.ExecuteFixturesPhase;
import com.bitresolution.xtest.phases.generate.CompileFixturesPhase;
import com.bitresolution.xtest.phases.reporting.ProcessReportPhase;
import com.bitresolution.xtest.phases.sources.GenerateSourcesPhase;
import com.bitresolution.xtest.phases.sources.SourceBuilder;
import com.bitresolution.xtest.phases.sources.SourceConfiguration;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.VerboseMockitoJUnitRunner;
import spock.lang.Specification;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@Category(Unit.class)
@RunWith(VerboseMockitoJUnitRunner.class)
public class XTestStandardContextSpec extends Specification {

    @Mock
    private Publisher publisher;

    @Mock
    private SourceBuilder sourceBuilder;

    @Mock
    private GraphBuilder graphBuilder;

    @Test
    public void shouldCreateDefaultLifecycle() throws LifecycleExecutorException {
        //given:
        SourceConfiguration configuration = new SourceConfiguration();

        GenerateSourcesPhase phase1 = new GenerateSourcesPhase(publisher, sourceBuilder, configuration);
        CompileGraphPhase phase2 = new CompileGraphPhase(publisher, graphBuilder);
        CompileFixturesPhase phase3 = new CompileFixturesPhase(publisher);
        ExecuteFixturesPhase phase4 = new ExecuteFixturesPhase(publisher);
        ProcessReportPhase phase5 = new ProcessReportPhase(publisher);

        XTestStandardContext context = new XTestStandardContext();
        context.setGenerateSourcesPhase(phase1);
        context.setCompileGraphPhase(phase2);
        context.setCompileFixturesPhase(phase3);
        context.setExecuteFixturesPhase(phase4);
        context.setProcessReportPhase(phase5);

        //when:
        List<Phase<?, ?>> phases = Lists.newArrayList(context.lifecycle().iterator());

        //then:
        assertThat(phases, is(Arrays.<Phase<?, ?>>asList(phase1, phase2, phase3, phase4, phase5)));
    }

}
