package com.rainbow.datapermission.plugin.register;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 * @description Register
 * @date 2020-06-01
 */
public interface Register<T> {
    /**
     * Registry
     *
     * @param key     key
     * @param element element
     * @throws Exception Exception
     */
    void registry(String key, T element) throws Exception;

    /**
     * Look for elements
     *
     * @param type type
     * @return Element
     * @throws Exception Exception
     */
    T lookFor(String type) throws Exception;
}
