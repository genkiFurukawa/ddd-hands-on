package com.example.hands.on.usecase.lending.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LendingDto {
    private Long id;
    private Long userId;
    private Long bookItemId;
    private LocalDate lentDate;
    private LocalDate dueDate;
    private LocalDate returnedDate;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public LendingDto(Long id, Long userId, Long bookItemId, LocalDate lentDate, LocalDate dueDate, LocalDate returnedDate, String status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.bookItemId = bookItemId;
        this.lentDate = lentDate;
        this.dueDate = dueDate;
        this.returnedDate = returnedDate;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getBookItemId() {
        return bookItemId;
    }

    public LocalDate getLentDate() {
        return lentDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}