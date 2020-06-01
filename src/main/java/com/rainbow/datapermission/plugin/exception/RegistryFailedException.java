package com.rainbow.datapermission.plugin.exception;

/**
 * @author wabslygzj@163.com (Tony Li)
 * @copyright rainbow
 */
public class RegistryFailedException extends RuntimeException {
    public RegistryFailedException(Throwable cause) {
        super(cause);
    }

    public RegistryFailedException(String message) {
        super(message);
    }

    protected RegistryFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
