package com.bitresolution.xtest.transform;

import com.bitresolution.xtest.meta.NodeTransform;
import com.bitresolution.xtest.meta.NodeTransformerChain;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@NodeTransform(NodeTransformerChain.class)
public @interface Apply {

    Class<? extends NodeTransform>[] value();
}
