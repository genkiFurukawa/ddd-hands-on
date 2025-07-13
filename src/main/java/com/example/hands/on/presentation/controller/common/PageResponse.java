package com.example.hands.on.presentation.controller.common;

import java.util.List;

public record PageResponse<T>(
    List<T> content,
    long totalElements,
    int totalPages,
    int size,
    int number
) {}