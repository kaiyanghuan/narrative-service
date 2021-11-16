package com.example.narrative.configurations;


import com.example.narrative.controllers.responses.ExceptionResponse;
import com.example.narrative.exceptions.BusinessException;
import com.example.narrative.exceptions.JwtAuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ExceptionResponse handleMethodArgumentNotValidExceptions(
            MethodArgumentNotValidException ex) {
        return ExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .errorMessage(Objects.requireNonNull(ex.getFieldError()).getDefaultMessage())
                .exceptionClass(ex.getClass().getCanonicalName())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ExceptionResponse handleBusinessExceptions(
            BusinessException ex) {
        return createExceptionResponse(ex, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(JwtAuthenticationException.class)
    @ResponseBody
    public ExceptionResponse handleJwtAuthenticationExceptions(
            JwtAuthenticationException ex) {
        return createExceptionResponse(ex, HttpStatus.UNAUTHORIZED);
    }

    private ExceptionResponse createExceptionResponse(Exception ex, HttpStatus status) {
        return ExceptionResponse.builder()
                .status(status)
                .errorMessage(ex.getLocalizedMessage())
                .exceptionClass(ex.getClass().getCanonicalName())
                .build();
    }
}