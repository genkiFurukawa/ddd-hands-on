package com.example.hands.on.presentation.controller.lending.request;

import java.time.LocalDate;

public record ExtendLendingRequest(
    LocalDate newDueDate
) {}