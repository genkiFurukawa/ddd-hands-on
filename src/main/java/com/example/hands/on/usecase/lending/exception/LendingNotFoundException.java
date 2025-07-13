package com.example.hands.on.usecase.lending.exception;

import com.example.hands.on.usecase.exception.NotFoundException;

public class LendingNotFoundException extends NotFoundException {
    
    public LendingNotFoundException(Long lendingId) {
        super("Lending not found: " + lendingId);
    }
    
    public LendingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}