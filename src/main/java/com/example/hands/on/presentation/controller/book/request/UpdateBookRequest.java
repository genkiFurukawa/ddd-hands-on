package com.example.hands.on.presentation.controller.book.request;

import java.util.List;

public record UpdateBookRequest(
    String title,
    List<AuthorRequest> authors,
    PublisherRequest publisher
) {
    public record AuthorRequest(String name) {}
    public record PublisherRequest(String name) {}
}