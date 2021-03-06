package com.bitresolution.xtest.phases.execute;

import com.bitresolution.xtest.core.lifecycle.LifecycleExecutorException;
import com.bitresolution.xtest.core.lifecycle.Phase;
import com.bitresolution.xtest.eventbus.Publisher;
import com.bitresolution.xtest.phases.generate.Fixtures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

import static com.bitresolution.xtest.events.CompleteEvent.complete;
import static com.bitresolution.xtest.events.StartEvent.start;

@Component
public class ExecuteFixturesPhase implements Phase<Fixtures, Report> {

    private static final Logger log = LoggerFactory.getLogger(ExecuteFixturesPhase.class);

    private final Publisher publisher;

    @Autowired
    public ExecuteFixturesPhase(Publisher publisher) {
        this.publisher = publisher;
    }

    @NotNull
    @Override
    public Class<Fixtures> getInputType() {
        return Fixtures.class;
    }

    @NotNull
    @Override
    public Class<Report> getOutputType() {
        return Report.class;
    }

    @NotNull
    @Override
    public Report execute(@NotNull Fixtures input) throws LifecycleExecutorException {
        log.debug("Executing {} fixtures", input.size());
        publisher.publish(start(this));
        Report report = new Report();
        publisher.publish(complete(this));
        log.debug("Finished executing {} fixtures", input.size());
        return report;
    }

    @NotNull
    @Override
    public String getName() {
        return "execute-fixtures";
    }

    @Override
    public String toString() {
        return getName();
    }
}
