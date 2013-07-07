package com.bitresolution.xtest.cli;

import com.beust.jcommander.JCommander;
import com.bitresolution.xtest.core.Engine;
import com.bitresolution.xtest.core.XTestConfiguration;
import com.bitresolution.xtest.core.spring.context.DefaultAnnotationConfigApplicationContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bootstrap {

    private static final Logger log = LoggerFactory.getLogger(Bootstrap.class);

    private final XTestConfiguration configuration;

    public Bootstrap(XTestConfiguration configuration) {
        this.configuration = configuration;
    }

    public void execute() {
        DefaultAnnotationConfigApplicationContextFactory contextFactory = new DefaultAnnotationConfigApplicationContextFactory();

        Engine engine = new Engine(configuration, contextFactory);
        engine.start();
        try {
            engine.join();
        }
        catch (InterruptedException e) {
            log.error("Error waiting for engine to complete", e);
        }
    }

    public static void main(String[] args) {
        XTestConfiguration configuration = new XTestConfiguration();
        new JCommander(configuration, args);

        new Bootstrap(configuration).execute();
    }
}
