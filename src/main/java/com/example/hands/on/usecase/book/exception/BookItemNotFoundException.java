package com.example.hands.on.usecase.book.exception;

import com.example.hands.on.usecase.common.dto.exception.NotFoundException;

public class BookItemNotFoundException extends NotFoundException {
    
    public BookItemNotFoundException(Long bookItemId) {
        super("BookItem not found: " + bookItemId);
    }
    
    public BookItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}