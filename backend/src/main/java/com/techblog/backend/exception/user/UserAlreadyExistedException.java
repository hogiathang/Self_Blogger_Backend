package com.techblog.backend.exception.user;

public class UserAlreadyExistedException extends RuntimeException {
    public UserAlreadyExistedException(String message) {
        super(message);
    }
}
