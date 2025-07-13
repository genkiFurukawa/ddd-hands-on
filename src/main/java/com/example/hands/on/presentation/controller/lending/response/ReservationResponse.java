package com.example.hands.on.presentation.controller.lending.response;

import java.time.LocalDate;

public record ReservationResponse(
    Long id,
    Long userId,
    String bookIsbn,
    BookSummary book,
    LocalDate reservedDate,
    LocalDate expiryDate,
    String status
) {
    public record BookSummary(
        String isbn,
        String title
    ) {}
}