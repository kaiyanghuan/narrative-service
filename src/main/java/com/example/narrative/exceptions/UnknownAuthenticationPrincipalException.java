package com.example.narrative.exceptions;

public class UnknownAuthenticationPrincipalException extends JwtAuthenticationException {
    public UnknownAuthenticationPrincipalException(String message){ super(message); }
}
