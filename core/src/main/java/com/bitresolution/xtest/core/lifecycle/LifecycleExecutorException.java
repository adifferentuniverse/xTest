package com.bitresolution.xtest.core.lifecycle;

import com.bitresolution.xtest.commons.FormattedMessageException;

public class LifecycleExecutorException extends FormattedMessageException {
    public LifecycleExecutorException(String message) {
        super(message);
    }

    public LifecycleExecutorException(String message, Object... parameters) {
        super(message, parameters);
    }
}
