package com.bitresolution.xtest;

import java.io.IOException;
import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface PromiseException {
    Class<? extends Throwable>[] value() default Throwable.class;
}
