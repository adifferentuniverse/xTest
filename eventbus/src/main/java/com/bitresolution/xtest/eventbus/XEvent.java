package com.bitresolution.xtest.eventbus;

import javax.validation.constraints.NotNull;

public interface XEvent {

    @NotNull
    Object getSource();
}
