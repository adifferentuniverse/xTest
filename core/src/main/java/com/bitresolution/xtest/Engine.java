package com.bitresolution.xtest;

import com.bitresolution.xtest.core.CoreConfiguration;
import com.bitresolution.xtest.core.lifecycle.Lifecycle;
import com.bitresolution.xtest.core.lifecycle.LifecycleExecutor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Engine extends Thread {

    private static final Logger log = LoggerFactory.getLogger(Engine.class);

    public Engine() {
        setName("engine");
    }

    public void run() {
        logBorderedMessage("Starting xTest");
        Class<CoreConfiguration> configurationClass = CoreConfiguration.class;
        try {
            AnnotationConfigApplicationContext context = buildApplicationContext(configurationClass);
            LifecycleExecutor executor = context.getBean(LifecycleExecutor.class);
            Lifecycle lifecycle = buildLifecycle();

            log.info("Executing lifecycle...");
            executor.execute(lifecycle);

            log.info("Lifecycle complete, shutting down xTest...");
            context.close();
        }
        catch (Exception e) {
            log.error("Terminal error: {}", e.getMessage(), e);
        }
        logBorderedMessage("Stopped xTest");
    }

    protected Lifecycle buildLifecycle() {
        return new Lifecycle();
    }

    protected AnnotationConfigApplicationContext buildApplicationContext(Class<?> configurationClass) {
        return new AnnotationConfigApplicationContext(configurationClass);
    }

    private void logBorderedMessage(String message) {
        log.info(StringUtils.repeat("-", 53));
        log.info("- " + StringUtils.rightPad(message, 50) + "-");
        log.info(StringUtils.repeat("-", 53));
    }

}
