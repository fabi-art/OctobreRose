package com.example.grace.exceptions;

public class DeletionException extends RuntimeException {
    public DeletionException(String message, Throwable cause) {
        super(message, cause);
    }
}
