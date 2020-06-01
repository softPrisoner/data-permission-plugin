package com.rainbow.datapermission.plugin.exception;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 */
public class SQLSyntaxException extends RuntimeException {

    public SQLSyntaxException(Throwable cause) {
        super(cause);
    }

    public SQLSyntaxException(String message) {
        super(message);
    }

    protected SQLSyntaxException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
