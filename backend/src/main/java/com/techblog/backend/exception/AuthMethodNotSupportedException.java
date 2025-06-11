package com.techblog.backend.exception;

public class AuthMethodNotSupportedException extends RuntimeException {
    public AuthMethodNotSupportedException(String message) {
        super(message);
    }
}
