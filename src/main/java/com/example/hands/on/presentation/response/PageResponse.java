package com.example.hands.on.presentation.response;

import java.util.List;

public record PageResponse<T>(
    List<T> content,
    long totalElements,
    int totalPages,
    int size,
    int number
) {}