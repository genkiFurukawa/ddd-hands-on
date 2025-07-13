package com.example.hands.on.usecase.user.command;

public class UpdateUserCommand {
    private Long id;
    private String name;
    private String email;
    private String status;

    public UpdateUserCommand(Long id, String name, String email, String status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.status = status;
    }

    public Long getId() {
        return id;
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
}