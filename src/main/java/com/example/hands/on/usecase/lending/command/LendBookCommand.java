package com.example.hands.on.usecase.lending.command;

public class LendBookCommand {
    private Long userId;
    private Long bookItemId;

    public LendBookCommand(Long userId, Long bookItemId) {
        this.userId = userId;
        this.bookItemId = bookItemId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getBookItemId() {
        return bookItemId;
    }
}