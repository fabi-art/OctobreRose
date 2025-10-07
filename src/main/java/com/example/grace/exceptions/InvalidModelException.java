package com.example.grace.exceptions;

//public class InvalidModelException {
//}
public class InvalidModelException extends CustomException {
    public InvalidModelException(String message) {
        super("INVALID_MODEL", message);
    }
}