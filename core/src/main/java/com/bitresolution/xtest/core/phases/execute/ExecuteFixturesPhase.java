package com.bitresolution.xtest.core.phases.execute;

import com.bitresolution.xtest.core.lifecycle.LifecycleExecutorException;
import com.bitresolution.xtest.core.lifecycle.Phase;
import com.bitresolution.xtest.core.phases.reporting.Report;
import com.bitresolution.xtest.events.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.bitresolution.xtest.core.events.CompleteEvent.complete;
import static com.bitresolution.xtest.core.events.StartEvent.start;

@Component
public class ExecuteFixturesPhase implements Phase<Fixtures, Report> {

    private static final Logger log = LoggerFactory.getLogger(ExecuteFixturesPhase.class);

    private final Publisher publisher;

    @Autowired
    public ExecuteFixturesPhase(Publisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public Class<Fixtures> getInputType() {
        return Fixtures.class;
    }

    @Override
    public Class<Report> getOutputType() {
        return Report.class;
    }

    @Override
    public Report execute(Fixtures input) throws LifecycleExecutorException {
        publisher.publish(start(this));
        Report report = new Report();
        publisher.publish(complete(this));
        return report;
    }

    @Override
    public String getName() {
        return "execute-fixtures";
    }

    @Override
    public String toString() {
        return getName();
    }
}
