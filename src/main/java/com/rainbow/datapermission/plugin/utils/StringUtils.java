package com.rainbow.datapermission.plugin.utils;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 */
public abstract class StringUtils {
    public static boolean isEmpty(Object str) {
        return str == null || "".equals(str);
    }
}
