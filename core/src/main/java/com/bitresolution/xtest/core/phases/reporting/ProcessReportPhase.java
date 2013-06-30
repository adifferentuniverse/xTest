package com.bitresolution.xtest.core.phases.reporting;

import com.bitresolution.xtest.core.lifecycle.LifecycleExecutorException;
import com.bitresolution.xtest.core.lifecycle.Phase;
import com.bitresolution.xtest.events.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.bitresolution.xtest.core.events.CompleteEvent.complete;
import static com.bitresolution.xtest.core.events.StartEvent.start;

@Component
public class ProcessReportPhase implements Phase<Report, Void> {

    private static final Logger log = LoggerFactory.getLogger(ProcessReportPhase.class);

    private final Publisher publisher;

    @Autowired
    public ProcessReportPhase(Publisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public Class<Report> getInputType() {
        return Report.class;
    }

    @Override
    public Class<Void> getOutputType() {
        return Void.class;
    }

    @Override
    public Void execute(Report input) throws LifecycleExecutorException {
        publisher.publish(start(this));
        publisher.publish(complete(this));
        return null;
    }

    @Override
    public String getName() {
        return "process-report";
    }

    @Override
    public String toString() {
        return getName();
    }
}
