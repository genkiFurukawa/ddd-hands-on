package com.example.hands.on.presentation.controller.lending.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record FulfillReservationRequest(
    @NotNull(message = "返却予定日は必須です")
    @Future(message = "返却予定日は未来の日付である必要があります")
    LocalDate dueDate
) {}