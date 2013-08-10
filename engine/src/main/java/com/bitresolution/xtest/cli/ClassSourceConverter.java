package com.bitresolution.xtest.cli;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;
import com.bitresolution.xtest.phases.sources.ClassSource;

public class ClassSourceConverter implements IStringConverter<ClassSource> {

    @Override
    public ClassSource convert(String s) {
        try {
            return new ClassSource(s);
        }
        catch (Exception e) {
            throw new ParameterException("Error parsing FullyQualifiedClassName '" + s + "', cause was: " +e);
        }
    }
}
