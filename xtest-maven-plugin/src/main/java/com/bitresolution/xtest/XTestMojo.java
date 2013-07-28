package com.bitresolution.xtest;

import com.bitresolution.xtest.XTestConfiguration;
import com.bitresolution.xtest.spring.context.DefaultAnnotationConfigApplicationContextFactory;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Goal which executes xTest tests found in /src/test/java
 *
 * @goal execute
 * @phase test
 */
public class XTestMojo extends AbstractMojo {

    public void execute() throws MojoExecutionException {
        XTestConfiguration configuration = new XTestConfiguration();
        Engine xTest = new Engine(configuration, new DefaultAnnotationConfigApplicationContextFactory());
        xTest.execute();
    }
}
