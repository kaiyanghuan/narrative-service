package com.example.narrative.exceptions;

public class JwtAuthenticationException extends RuntimeException {
    public JwtAuthenticationException(String errorMessage) {
        super(errorMessage);
    }
}
