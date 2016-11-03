/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/11/2.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AnnotationSingle {
    String getName() default "";
}
