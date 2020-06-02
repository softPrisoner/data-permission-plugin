package com.rainbow.datapermission.plugin.annotations;

import java.lang.annotation.*;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Extensions {
    //Elements
    DataPermission[] elements() default {};
}
