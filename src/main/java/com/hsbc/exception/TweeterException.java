package com.hsbc.exception;

/**
 * Created by mkilar on 30.08.2017.
 */
public class TweeterException extends Exception {

    public TweeterException() {
    }

    public TweeterException(String message) {
        super(message);
    }

    public TweeterException(String message, Throwable cause) {
        super(message, cause);
    }
}
