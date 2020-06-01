package com.rainbow.datapermission.plugin.exception;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 */
public class SQLConcatNotFoundException extends RuntimeException {

    public SQLConcatNotFoundException(Throwable cause) {
        super(cause);
    }

    public SQLConcatNotFoundException(String message) {
        super(message);
    }

    protected SQLConcatNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
