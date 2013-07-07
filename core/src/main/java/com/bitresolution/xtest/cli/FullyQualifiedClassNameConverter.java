package com.bitresolution.xtest.cli;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;
import com.bitresolution.succor.reflection.FullyQualifiedClassName;

public class FullyQualifiedClassNameConverter implements IStringConverter<FullyQualifiedClassName> {

    @Override
    public FullyQualifiedClassName convert(String s) {
        try {
            return new FullyQualifiedClassName(s);
        }
        catch (Exception e) {
            throw new ParameterException("Error parsing FullyQualifiedClassName '" + s + "', cause was: " +e);
        }
    }
}
