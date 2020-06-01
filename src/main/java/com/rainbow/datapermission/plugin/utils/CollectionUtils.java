package com.rainbow.datapermission.plugin.utils;

import java.util.Collection;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 */
public abstract class CollectionUtils {
    public CollectionUtils() {
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }
}
