package com.example.hands.on.usecase.user.command;

public class CreateUserCommand {
    private String name;
    private String email;

    public CreateUserCommand(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}