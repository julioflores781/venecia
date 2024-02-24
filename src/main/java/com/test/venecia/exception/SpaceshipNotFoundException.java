package com.test.venecia.exception;

public class SpaceshipNotFoundException extends RuntimeException {
    public SpaceshipNotFoundException(String message) {
        super(message);
    }
}
