package com.example.hands.on.usecase.book.exception;

import com.example.hands.on.usecase.exception.NotFoundException;

public class BookNotFoundException extends NotFoundException {
    
    public BookNotFoundException(Long bookId) {
        super("Book not found: " + bookId);
    }
    
    public BookNotFoundException(String isbn) {
        super("Book not found with ISBN: " + isbn);
    }
    
    public BookNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}