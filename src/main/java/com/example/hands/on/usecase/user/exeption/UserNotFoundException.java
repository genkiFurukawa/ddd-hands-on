package com.example.hands.on.usecase.user.exeption;

import com.example.hands.on.usecase.common.dto.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    
    public UserNotFoundException(Long userId) {
        super("User not found: " + userId);
    }
    
    public UserNotFoundException(String email) {
        super("User not found with email: " + email);
    }
    
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}