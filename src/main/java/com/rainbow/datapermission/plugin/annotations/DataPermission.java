package com.rainbow.datapermission.plugin.annotations;

import java.lang.annotation.*;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataPermission {
    //method name
    String method() default "";

    //Set ignore children
    String[] ignoreChildren() default {};

    //Set ignore params
    String[] ignoreParams() default {};
}

