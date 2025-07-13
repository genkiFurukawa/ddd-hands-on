package com.example.hands.on.domain.model.lending.entity;

import com.example.hands.on.domain.model.book.entity.Book;
import com.example.hands.on.domain.model.lending.value.ReservationStatus;
import com.example.hands.on.domain.model.user.entity.User;

import java.time.LocalDate;

public record Reservation(Long id, User user, Book book, LocalDate reservedDate, LocalDate expiryDate, ReservationStatus status) {
    
    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }
    
    public boolean isActive() {
        return status == ReservationStatus.WAITING && !isExpired();
    }
}