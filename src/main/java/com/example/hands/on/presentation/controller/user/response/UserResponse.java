package com.example.hands.on.presentation.controller.user.response;

import java.time.LocalDate;

public record UserResponse(
    Long id,
    String name,
    String email,
    LocalDate registeredDate,
    String status
) {}