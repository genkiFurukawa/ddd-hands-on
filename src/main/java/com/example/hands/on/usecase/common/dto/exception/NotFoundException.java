package com.example.hands.on.usecase.common.dto.exception;

public abstract class NotFoundException extends DomainException {
    
    protected NotFoundException(String message) {
        super(message);
    }
    
    protected NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}