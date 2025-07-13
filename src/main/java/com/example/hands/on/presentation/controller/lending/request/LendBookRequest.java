package com.example.hands.on.presentation.controller.lending.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record LendBookRequest(
    @NotNull(message = "ユーザーIDは必須です")
    Long userId,
    
    @NotNull(message = "蔵書IDは必須です")
    Long bookItemId,
    
    @NotNull(message = "返却予定日は必須です")
    @Future(message = "返却予定日は未来の日付である必要があります")
    LocalDate dueDate
) {}