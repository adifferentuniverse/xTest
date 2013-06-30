package com.bitresolution.xtest;

import com.bitresolution.succor.reflection.FullyQualifiedClassName;
import com.bitresolution.xtest.core.Engine;
import com.bitresolution.xtest.core.SourceConfiguration;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.util.Properties;

/**
 * Goal which executes xTest tests found in /src/test/java
 *
 * @goal execute
 * @phase test
 */
public class XTestMojo extends AbstractMojo {

    public void execute() throws MojoExecutionException {
        FullyQualifiedClassName configurationClass = new FullyQualifiedClassName(XTestDefaultContext.class);
        SourceConfiguration sourceConfiguration = new SourceConfiguration();
        Properties properties = new Properties();
        Engine xTest = new Engine(configurationClass, sourceConfiguration, properties, null);
        xTest.execute();
    }
}
