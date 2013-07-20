package com.bitresolution.xtest.events;

import javax.validation.constraints.NotNull;

public interface XEvent {

    @NotNull
    Object getSource();
}
