package com.example.hands.on.domain.model.user.entity;

import com.example.hands.on.domain.model.user.value.UserStatus;

import java.time.LocalDate;

public record User(Long id, String name, String email, LocalDate registeredDate, UserStatus status) {
}