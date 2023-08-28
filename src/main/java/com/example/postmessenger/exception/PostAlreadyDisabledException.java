package com.example.postmessenger.exception;

public class PostAlreadyDisabledException extends RuntimeException {
    public PostAlreadyDisabledException(String message) {
        super(message);
    }
}
