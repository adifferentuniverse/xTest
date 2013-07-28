package com.bitresolution.xtest;

import com.bitresolution.xtest.core.XTestContext;
import com.bitresolution.xtest.core.lifecycle.Lifecycle;
import com.bitresolution.xtest.core.lifecycle.LifecycleExecutorException;
import com.bitresolution.xtest.core.phases.compile.CompileGraphPhase;
import com.bitresolution.xtest.core.phases.execute.ExecuteFixturesPhase;
import com.bitresolution.xtest.core.phases.generate.CompileFixturesPhase;
import com.bitresolution.xtest.core.phases.reporting.ProcessReportPhase;
import com.bitresolution.xtest.core.phases.sources.GenerateSourcesPhase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XTestStandardContext extends XTestContext {

    @Autowired
    private GenerateSourcesPhase generateSourcesPhase;
    @Autowired
    private CompileGraphPhase compileGraphPhase;
    @Autowired
    private CompileFixturesPhase compileFixturesPhase;
    @Autowired
    private ExecuteFixturesPhase executeFixturesPhase;
    @Autowired
    private ProcessReportPhase processReportPhase;

    @Override
    @Bean
    public Lifecycle lifecycle() throws LifecycleExecutorException {
        Lifecycle lifecycle = new Lifecycle();
        lifecycle.add(generateSourcesPhase);
        lifecycle.add(compileGraphPhase);
        lifecycle.add(compileFixturesPhase);
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

    public void setCompileFixturesPhase(CompileFixturesPhase compileFixturesPhase) {
        this.compileFixturesPhase = compileFixturesPhase;
    }

    public void setExecuteFixturesPhase(ExecuteFixturesPhase executeFixturesPhase) {
        this.executeFixturesPhase = executeFixturesPhase;
    }

    public void setProcessReportPhase(ProcessReportPhase processReportPhase) {
        this.processReportPhase = processReportPhase;
    }
}
