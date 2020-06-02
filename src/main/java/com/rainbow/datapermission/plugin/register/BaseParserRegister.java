package com.rainbow.datapermission.plugin.register;

import com.rainbow.datapermission.plugin.parser.Parser;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 */
public final class BaseParserRegister extends AbstractSQLParserRegister {

    private BaseParserRegister() {

    }

    public static Register<Parser> getInstance() {
        return BaseParserRegisterHolder.INSTANCE;
    }

    private static final class BaseParserRegisterHolder {
        private static final Register<Parser> INSTANCE = new BaseParserRegister();
    }
}
