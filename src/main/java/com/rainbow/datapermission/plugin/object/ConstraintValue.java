package com.rainbow.datapermission.plugin.object;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 */
public abstract class ConstraintValue<T> implements Value<T> {
    private T value;

    @Override
    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public T getValue() {
        return this.value;
    }
}
