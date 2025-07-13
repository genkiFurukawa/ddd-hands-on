package com.example.hands.on.presentation.controller.lending.response;

import java.time.LocalDate;

public record LendingResponse(
    Long id,
    Long userId,
    String userName,
    Long bookItemId,
    BookSummary book,
    LocalDate lentDate,
    LocalDate dueDate,
    LocalDate returnedDate,
    String status,
    Integer overdueDays
) {
    public record BookSummary(
        String isbn,
        String title
    ) {}
}