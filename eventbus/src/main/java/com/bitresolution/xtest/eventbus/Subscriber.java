package com.bitresolution.xtest.eventbus;

import javax.validation.constraints.NotNull;

public interface Subscriber {
    void process(@NotNull XEvent event);
}
