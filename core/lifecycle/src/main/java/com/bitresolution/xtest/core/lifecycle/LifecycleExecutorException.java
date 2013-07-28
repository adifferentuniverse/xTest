package com.bitresolution.xtest.core.lifecycle;

import com.bitresolution.succor.exceptions.FormattedMessageException;

public class LifecycleExecutorException extends FormattedMessageException {

    private static final long serialVersionUID = 1L;

    public LifecycleExecutorException(String message) {
        super(message);
    }

    public LifecycleExecutorException(String message, Object... parameters) {
        super(message, parameters);
    }
}
