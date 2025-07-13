package com.example.hands.on.usecase.book.command;

public class UpdateBookItemCommand {
    private Long id;
    private String location;
    private String status;

    public UpdateBookItemCommand(Long id, String location, String status) {
        this.id = id;
        this.location = location;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getStatus() {
        return status;
    }
}