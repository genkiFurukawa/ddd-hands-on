package com.example.hands.on.usecase.lending.dto;

public class ReservationFulfillmentDto {
    private ReservationDto reservation;
    private LendingDto lending;

    public ReservationFulfillmentDto(ReservationDto reservation, LendingDto lending) {
        this.reservation = reservation;
        this.lending = lending;
    }

    public ReservationDto getReservation() {
        return reservation;
    }

    public LendingDto getLending() {
        return lending;
    }
}