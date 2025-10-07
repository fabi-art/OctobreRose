package com.example.grace.exceptions;

//public class DataAccessException {
//
//}


public class DataAccessException extends RuntimeException {
    private final String code;

    public DataAccessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
