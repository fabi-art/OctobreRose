package com.example.grace.exceptions;


public class CentreSanteNotFoundException extends CustomException {
    public CentreSanteNotFoundException(String message) {
        super("CENTRE_NOT_FOUND", message);
    }
}