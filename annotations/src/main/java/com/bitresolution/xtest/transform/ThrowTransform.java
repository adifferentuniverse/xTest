package com.bitresolution.xtest.transform;

import com.bitresolution.xtest.meta.NodeTransform;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@NodeTransform(ThrowTransformer.class)
public @interface ThrowTransform {
    Class<?> targetClass();
    String targetMethod();
    Class<? extends Throwable>[] type() default Exception.class;
}
