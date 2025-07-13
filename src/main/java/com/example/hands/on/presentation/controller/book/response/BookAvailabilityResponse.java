package com.example.hands.on.presentation.controller.book.response;

public record BookAvailabilityResponse(
    String isbn,
    int totalItems,
    int availableItems,
    int lentItems,
    int reservedItems,
    int unavailableItems,
    int waitingReservations
) {}