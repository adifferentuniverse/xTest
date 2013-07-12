package com.bitresolution.xtest.core.phases.sources;

import com.bitresolution.succor.junit.category.Integration;
import com.bitresolution.succor.reflection.FullyQualifiedClassName;
import com.bitresolution.succor.reflection.PackageName;
import com.bitresolution.xtest.core.XTestConfiguration;
import com.bitresolution.xtest.events.Publisher;
import com.bitresolution.xtest.examples.*;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.VerboseMockitoJUnitRunner;

import java.util.Set;

import static com.bitresolution.xtest.core.events.CompleteEvent.complete;
import static com.bitresolution.xtest.core.events.StartEvent.start;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@Category(Integration.class)
@RunWith(VerboseMockitoJUnitRunner.class)
public class GenerateSourcesPhaseTest {

    @Mock
    private Publisher publisher;

    @Test
    public void shouldGenerateEmptySourcesWhenNoSourcesSpecified() throws Exception {
        //given
        XTestConfiguration configuration = new XTestConfiguration();
        DefaultSourceBuilder builder = new DefaultSourceBuilder();
        GenerateSourcesPhase phase = new GenerateSourcesPhase(publisher, builder, configuration);

        //when
        Sources sources = phase.execute(null);

        //then
        assertThat(sources.isEmpty(), is(true));
        verify(publisher).publish(start(phase));
        verify(publisher).publish(complete(phase));
        verifyNoMoreInteractions(publisher);
    }

    @Test
    public void shouldGenerateSourcesFromConfiguration() throws Exception {
        //given
        XTestConfiguration configuration = new XTestConfiguration();
        SourceConfiguration sources = configuration.getSourceConfiguration();
        sources.setIncludedClasses(asList(new ClassSource(GenerateSourcesPhase.class.getCanonicalName())));
        sources.setExcludedClasses(asList(new ClassSource(Object.class.getCanonicalName())));
        sources.setIncludedPackages(asList(new XTestAnnotatedSource(new PackageName("com.bitresolution.xtest.examples"))));
        sources.setExcludedPackages(asList(new XTestAnnotatedSource(new PackageName("this.package.should.not.exist"))));

        DefaultSourceBuilder builder = new DefaultSourceBuilder();
        GenerateSourcesPhase phase = new GenerateSourcesPhase(publisher, builder, configuration);

        //when
        Sources output = phase.execute(null);

        //then
        Set<FullyQualifiedClassName> expectedClasses = Sets.newHashSet(
                new FullyQualifiedClassName(GenerateSourcesPhase.class),
                new FullyQualifiedClassName(TestNodeClassWithNoTestNodeMethodsExample.class),
                new FullyQualifiedClassName(TestNodeEmptyClassExample.class),
                new FullyQualifiedClassName(TestNodeExceptionHandlingMethodExample.class),
                new FullyQualifiedClassName(TestNodeMultipleMethodExample.class),
                new FullyQualifiedClassName(TestNodeSingleMethodExample.class)
        );

        Sources expectedSources = new Sources(expectedClasses);
        assertThat(output, is(expectedSources));
        verify(publisher).publish(start(phase));
        verify(publisher).publish(complete(phase));
        verifyNoMoreInteractions(publisher);
    }
}
