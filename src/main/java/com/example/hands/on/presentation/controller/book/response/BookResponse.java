package com.example.hands.on.presentation.controller.book.response;

import java.util.List;

public record BookResponse(
    String isbn,
    String title,
    List<AuthorResponse> authors,
    PublisherResponse publisher
) {
    public record AuthorResponse(String name) {}
    public record PublisherResponse(String name) {}
}