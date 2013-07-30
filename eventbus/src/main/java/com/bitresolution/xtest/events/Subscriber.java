package com.bitresolution.xtest.events;

import javax.validation.constraints.NotNull;

public interface Subscriber {
    void process(@NotNull XEvent event);
}
