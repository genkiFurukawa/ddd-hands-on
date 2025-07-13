package com.example.hands.on.usecase.lending.command;

public class CreateReservationCommand {
    private Long userId;
    private String isbn;

    public CreateReservationCommand(Long userId, String isbn) {
        this.userId = userId;
        this.isbn = isbn;
    }

    public Long getUserId() {
        return userId;
    }

    public String getIsbn() {
        return isbn;
    }
}