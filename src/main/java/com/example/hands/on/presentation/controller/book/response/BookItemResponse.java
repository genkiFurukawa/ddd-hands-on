package com.example.hands.on.presentation.controller.book.response;

public record BookItemResponse(
    Long id,
    String isbn,
    String status
) {}