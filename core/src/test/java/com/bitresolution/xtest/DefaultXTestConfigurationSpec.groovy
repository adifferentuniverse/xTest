package com.bitresolution.xtest

import com.bitresolution.xtest.core.lifecycle.Phase
import com.bitresolution.xtest.core.phases.compile.CompileGraphPhase
import com.bitresolution.xtest.core.phases.execute.ExecuteFixturesPhase
import com.bitresolution.xtest.core.phases.generate.GenerateFixturesPhase
import com.bitresolution.xtest.core.phases.reporting.ProcessReportPhase
import com.bitresolution.xtest.core.phases.sources.GenerateSourcesPhase
import com.bitresolution.xtest.events.Publisher
import spock.lang.Specification

class DefaultXTestConfigurationSpec extends Specification {

    def "shouldCreateDefaultLifecycle"() {
        given:
        Publisher publisher = Mock(Publisher)
        DefaultXTestConfiguration configuration = new DefaultXTestConfiguration()
        configuration.generateSourcesPhase = new GenerateSourcesPhase(publisher)
        configuration.compileGraphPhase = new CompileGraphPhase(publisher)
        configuration.generateFixturesPhase = new GenerateFixturesPhase(publisher)
        configuration.executeFixturesPhase = new ExecuteFixturesPhase(publisher)
        configuration.processReportPhase = new ProcessReportPhase(publisher)

        when:
        List<Phase<?, ?>> phases = configuration.lifecycle().toList()

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
