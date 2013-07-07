package com.bitresolution.xtest;

import com.bitresolution.xtest.core.Engine;
import com.bitresolution.xtest.core.XTestConfiguration;
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
        XTestConfiguration configuration = new XTestConfiguration();
        Properties properties = new Properties();
        Engine xTest = new Engine(configuration, null);
        xTest.execute();
    }
}
