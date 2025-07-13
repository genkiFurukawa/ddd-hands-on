package com.example.hands.on.usecase.user.command;

public class SearchUsersCommand {
    private String name;
    private String email;
    private String status;
    private int page;
    private int size;

    public SearchUsersCommand(String name, String email, String status, int page, int size) {
        this.name = name;
        this.email = email;
        this.status = status;
        this.page = page;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }
}