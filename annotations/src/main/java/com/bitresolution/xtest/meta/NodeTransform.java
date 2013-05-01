package com.bitresolution.xtest.meta;

import java.lang.annotation.*;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface NodeTransform {

    Class<? extends NodeTransformer> value();
}
