package com.example.hands.on.presentation.controller.lending.response;

public record ReservationFulfillmentResponse(
    LendingResponse lending,
    ReservationSummary reservation
) {
    public record ReservationSummary(
        Long id,
        String status
    ) {}
}