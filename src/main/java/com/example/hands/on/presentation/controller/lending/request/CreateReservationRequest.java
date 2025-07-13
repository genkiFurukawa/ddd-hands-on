package com.example.hands.on.presentation.controller.lending.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreateReservationRequest(
    @NotNull(message = "ユーザーIDは必須です")
    Long userId,
    
    @NotBlank(message = "ISBNは必須です")
    @Pattern(regexp = "^(97[89])?[0-9]{9}[0-9X]$", message = "ISBN形式が不正です")
    String bookIsbn
) {}