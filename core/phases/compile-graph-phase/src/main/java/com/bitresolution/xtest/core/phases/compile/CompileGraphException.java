package com.bitresolution.xtest.core.phases.compile;

import com.bitresolution.succor.exceptions.FormattedMessageException;

public class CompileGraphException extends FormattedMessageException {

    private static final long serialVersionUID = 1L;

    public CompileGraphException(String s) {
        super(s);
    }

    public CompileGraphException(String s, Object... parameters) {
        super(s, parameters);
    }
}
