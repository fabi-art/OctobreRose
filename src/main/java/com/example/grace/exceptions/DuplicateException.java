package com.example.grace.exceptions;

public class DuplicateException extends CustomException{

    public DuplicateException(String message) {
        super("DUPLICATE", message);
    }
}
