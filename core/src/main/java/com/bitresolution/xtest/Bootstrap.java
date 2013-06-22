package com.bitresolution.xtest;

import com.bitresolution.xtest.core.CoreConfiguration;

public class Bootstrap {
    public static void main(String[] args) {

        Engine engine = new Engine(CoreConfiguration.class);
        engine.start();
    }
}
