package com.rainbow.datapermission.plugin.register;

import com.rainbow.datapermission.plugin.concat.SQLConcat;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 */
public final class BaseConcatRegister extends AbstractSQLConcatRegister {
    private BaseConcatRegister() {

    }

    public static Register<SQLConcat> getInstance() {
        return BaseConcatRegisterHolder.INSTANCE;
    }

    private static final class BaseConcatRegisterHolder {
        private static final Register<SQLConcat> INSTANCE = new BaseConcatRegister();
    }
}
