package com.techblog.backend.exception;

public class IlegalArgumentException extends RuntimeException {
    public IlegalArgumentException(String message) {
        super(message);
    }
}
