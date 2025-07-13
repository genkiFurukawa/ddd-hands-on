package com.example.hands.on.presentation.controller.book.request;

public record CreateBookItemRequest(
    String isbn,
    String status
) {}