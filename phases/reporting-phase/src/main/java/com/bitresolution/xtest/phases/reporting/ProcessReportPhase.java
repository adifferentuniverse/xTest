package com.bitresolution.xtest.phases.reporting;

import com.bitresolution.xtest.core.lifecycle.LifecycleExecutorException;
import com.bitresolution.xtest.core.lifecycle.Phase;
import com.bitresolution.xtest.eventbus.Publisher;
import com.bitresolution.xtest.phases.execute.Report;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

import static com.bitresolution.xtest.events.CompleteEvent.complete;
import static com.bitresolution.xtest.events.StartEvent.start;

@Component
public class ProcessReportPhase implements Phase<Report, Void> {

    private static final Logger log = LoggerFactory.getLogger(ProcessReportPhase.class);

    private final Publisher publisher;

    @Autowired
    public ProcessReportPhase(Publisher publisher) {
        this.publisher = publisher;
    }

    @NotNull
    @Override
    public Class<Report> getInputType() {
        return Report.class;
    }

    @NotNull
    @Override
    public Class<Void> getOutputType() {
        return Void.class;
    }

    @NotNull
    @Override
    public Void execute(@NotNull Report input) throws LifecycleExecutorException {
        publisher.publish(start(this));
        publisher.publish(complete(this));
        return null;
    }

    @NotNull
    @Override
    public String getName() {
        return "process-report";
    }

    @Override
    public String toString() {
        return getName();
    }
}
