package com.test.venecia.exception;

public class KafkaSendFailedException extends RuntimeException {
    public KafkaSendFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}