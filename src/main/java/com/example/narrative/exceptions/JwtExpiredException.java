package com.example.narrative.exceptions;

public class JwtExpiredException extends JwtAuthenticationException {
    public JwtExpiredException(String errorMessage) {
        super(errorMessage);
    }
}
