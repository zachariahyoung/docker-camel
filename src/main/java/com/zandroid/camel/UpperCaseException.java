package com.zandroid.camel;

public class UpperCaseException extends RuntimeException {

    public UpperCaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpperCaseException(String message) {
        super(message);
    }

    public UpperCaseException() {
    }
}
