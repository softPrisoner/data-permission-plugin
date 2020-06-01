package com.rainbow.datapermission.plugin.exception;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 */
public class UnRegisterException extends RuntimeException {

    public UnRegisterException(Throwable cause) {
        super(cause);
    }

    public UnRegisterException(String message) {
        super(message);
    }

    protected UnRegisterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
