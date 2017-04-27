package com.harry.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2017/4/27.
 */

@Target({java.lang.annotation.ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewClick {
    int[] value();
}
