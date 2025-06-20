package com.techblog.backend.exception.user;

public class AuthMethodNotSupportedException extends RuntimeException {
    public AuthMethodNotSupportedException(String message) {
        super(message);
    }
}
