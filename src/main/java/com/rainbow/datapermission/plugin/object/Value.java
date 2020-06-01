package com.rainbow.datapermission.plugin.object;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 */
public interface Value<T> {
    /**
     * Set Value
     *
     * @param value T
     */
    void setValue(T value);

    /**
     * Get Value
     *
     * @return T
     */
    T getValue();
}
