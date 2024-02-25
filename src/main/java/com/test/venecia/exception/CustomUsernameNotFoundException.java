package com.test.venecia.exception;

public class CustomUsernameNotFoundException extends RuntimeException {
    public CustomUsernameNotFoundException(String message) {
        super(message);
    }
}