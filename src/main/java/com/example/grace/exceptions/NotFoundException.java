package com.example.grace.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    // pratique: construire un message standardisé depuis un type + id
    public NotFoundException(Class<?> type, Object id) {
        super(type.getSimpleName() + " introuvable (id=" + id + ")");
    }
}
