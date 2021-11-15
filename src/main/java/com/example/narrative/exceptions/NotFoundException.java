package com.example.narrative.exceptions;

public class NotFoundException extends BusinessException {
    public NotFoundException(String message) {
        super(message);
    }
}
