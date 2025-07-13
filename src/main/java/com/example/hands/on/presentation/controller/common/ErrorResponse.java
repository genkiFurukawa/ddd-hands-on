package com.example.hands.on.presentation.controller.common;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
    LocalDateTime timestamp,
    int status,
    String error,
    String message,
    List<FieldError> details,
    String path
) {
    public record FieldError(
        String field,
        String message
    ) {}
}