package com.bitresolution.xtest.core.phases.parse;

import com.bitresolution.xtest.core.FormattedMessageException;

public class TestGraphException extends FormattedMessageException {

    public TestGraphException(String s) {
        super(s);
    }

    public TestGraphException(String s, Object... parameters) {
        super(s, parameters);
    }
}
