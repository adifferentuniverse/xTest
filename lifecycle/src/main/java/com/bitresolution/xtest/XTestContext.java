package com.bitresolution.xtest;

import com.bitresolution.xtest.core.CoreContext;
import com.bitresolution.xtest.core.lifecycle.Lifecycle;
import com.bitresolution.xtest.core.lifecycle.LifecycleExecutorException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(CoreContext.class)
public class XTestContext {

    @Bean
    public Lifecycle lifecycle() throws LifecycleExecutorException {
        return new Lifecycle();
    }
}
