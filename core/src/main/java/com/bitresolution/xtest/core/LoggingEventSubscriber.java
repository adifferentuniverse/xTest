package com.bitresolution.xtest.core;

import com.bitresolution.xtest.events.Subscriber;
import com.bitresolution.xtest.events.XEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class LoggingEventSubscriber implements Subscriber {

    private static final Logger log = LoggerFactory.getLogger(LoggingEventSubscriber.class);

    @Override
    public void process(@NotNull XEvent event) {
        log.info(event.toString());
    }
}
