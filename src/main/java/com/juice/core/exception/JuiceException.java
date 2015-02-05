package com.juice.core.exception;

/**
 * JuiceException
 *
 * @author wangqing
 * @since 1.0.0
 */
public class JuiceException extends Exception {

    public JuiceException(String msg) {
        super(msg);
    }

    public JuiceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
