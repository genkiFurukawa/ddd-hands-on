package com.example.hands.on.usecase.lending.command;

public class FulfillReservationCommand {
    private Long reservationId;
    private Long bookItemId;

    public FulfillReservationCommand(Long reservationId, Long bookItemId) {
        this.reservationId = reservationId;
        this.bookItemId = bookItemId;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public Long getBookItemId() {
        return bookItemId;
    }
}