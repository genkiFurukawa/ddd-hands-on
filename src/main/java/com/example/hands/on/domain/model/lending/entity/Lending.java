package com.example.hands.on.domain.model.lending.entity;

import com.example.hands.on.domain.model.book.entity.BookItem;
import com.example.hands.on.domain.model.lending.value.LendingStatus;
import com.example.hands.on.domain.model.user.entity.User;

import java.time.LocalDate;

public record Lending(Long id, User user, BookItem bookItem, LocalDate lentDate, LocalDate dueDate, LocalDate returnedDate, LendingStatus status) {
    
    public boolean isOverdue() {
        return returnedDate == null && LocalDate.now().isAfter(dueDate);
    }
    
    public boolean isReturned() {
        return returnedDate != null;
    }
    
    public LendingStatus getCurrentStatus() {
        if (isReturned()) {
            return LendingStatus.RETURNED;
        }
        if (isOverdue()) {
            return LendingStatus.OVERDUE;
        }
        return LendingStatus.ACTIVE;
    }
}