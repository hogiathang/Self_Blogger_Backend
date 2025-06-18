package com.techblog.backend.exception;

public class UserAlreadyExistedException extends RuntimeException {
    public UserAlreadyExistedException(String message) {
        super(message);
    }
}
