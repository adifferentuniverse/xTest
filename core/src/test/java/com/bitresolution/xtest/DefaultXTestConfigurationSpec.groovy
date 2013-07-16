package com.bitresolution.xtest

import com.bitresolution.xtest.core.XTestConfiguration
import com.bitresolution.xtest.core.lifecycle.Phase
import com.bitresolution.xtest.core.phases.compile.CompileGraphPhase
import com.bitresolution.xtest.core.phases.compile.GraphBuilder
import com.bitresolution.xtest.core.phases.execute.ExecuteFixturesPhase
import com.bitresolution.xtest.core.phases.generate.GenerateFixturesPhase
import com.bitresolution.xtest.core.phases.reporting.ProcessReportPhase
import com.bitresolution.xtest.core.phases.sources.GenerateSourcesPhase
import com.bitresolution.xtest.core.phases.sources.SourceBuilder
import com.bitresolution.xtest.events.Publisher
import spock.lang.Specification

class DefaultXTestConfigurationSpec extends Specification {

    def "shouldCreateDefaultLifecycle"() {
        given:
        Publisher publisher = Mock(Publisher)
        XTestConfiguration configuration = Mock(XTestConfiguration)

        XTestDefaultContext context = new XTestDefaultContext()
        context.generateSourcesPhase = new GenerateSourcesPhase(publisher, Mock(SourceBuilder), configuration)
        context.compileGraphPhase = new CompileGraphPhase(publisher, Mock(GraphBuilder))
        context.generateFixturesPhase = new GenerateFixturesPhase(publisher)
        context.executeFixturesPhase = new ExecuteFixturesPhase(publisher)
        context.processReportPhase = new ProcessReportPhase(publisher)

        when:
        List<Phase<?, ?>> phases = context.lifecycle().toList()

        then:
        assert phases*.class == [
                GenerateSourcesPhase,
                CompileGraphPhase,
                GenerateFixturesPhase,
                ExecuteFixturesPhase,
                ProcessReportPhase
        ]
    }
}
