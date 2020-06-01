package com.rainbow.datapermission.plugin.exception;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 */
public class SQLParserNotFoundException extends RuntimeException {

    public SQLParserNotFoundException(Throwable cause) {
        super(cause);
    }

    public SQLParserNotFoundException(String message) {
        super(message);
    }

    protected SQLParserNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
