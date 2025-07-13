package com.example.hands.on.usecase.book.command;

public class CreateBookItemCommand {
    private String isbn;
    private String location;

    public CreateBookItemCommand(String isbn, String location) {
        this.isbn = isbn;
        this.location = location;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getLocation() {
        return location;
    }
}