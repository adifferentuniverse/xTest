package com.bitresolution.xtest;

import com.bitresolution.xtest.core.CoreContext;
import com.bitresolution.xtest.core.lifecycle.Lifecycle;
import com.bitresolution.xtest.core.lifecycle.LifecycleExecutorException;
import com.bitresolution.xtest.core.phases.compile.CompileGraphPhase;
import com.bitresolution.xtest.core.phases.execute.ExecuteFixturesPhase;
import com.bitresolution.xtest.core.phases.generate.GenerateFixturesPhase;
import com.bitresolution.xtest.core.phases.reporting.ProcessReportPhase;
import com.bitresolution.xtest.core.phases.sources.GenerateSourcesPhase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CoreContext.class})
public class XTestDefaultContext {

    @Autowired
    private GenerateSourcesPhase generateSourcesPhase;
    @Autowired
    private CompileGraphPhase compileGraphPhase;
    @Autowired
    private GenerateFixturesPhase generateFixturesPhase;
    @Autowired
    private ExecuteFixturesPhase executeFixturesPhase;
    @Autowired
    private ProcessReportPhase processReportPhase;

    @Bean
    public Lifecycle lifecycle() throws LifecycleExecutorException {
        Lifecycle lifecycle = new Lifecycle();
        lifecycle.add(generateSourcesPhase);
        lifecycle.add(compileGraphPhase);
        lifecycle.add(generateFixturesPhase);
        lifecycle.add(executeFixturesPhase);
        lifecycle.add(processReportPhase);
        return lifecycle;
    }

    public void setGenerateSourcesPhase(GenerateSourcesPhase generateSourcesPhase) {
        this.generateSourcesPhase = generateSourcesPhase;
    }

    public void setCompileGraphPhase(CompileGraphPhase compileGraphPhase) {
        this.compileGraphPhase = compileGraphPhase;
    }

    public void setGenerateFixturesPhase(GenerateFixturesPhase generateFixturesPhase) {
        this.generateFixturesPhase = generateFixturesPhase;
    }

    public void setExecuteFixturesPhase(ExecuteFixturesPhase executeFixturesPhase) {
        this.executeFixturesPhase = executeFixturesPhase;
    }

    public void setProcessReportPhase(ProcessReportPhase processReportPhase) {
        this.processReportPhase = processReportPhase;
    }
}
