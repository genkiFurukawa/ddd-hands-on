package com.example.hands.on.usecase.lending.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReservationDto {
    private Long id;
    private Long userId;
    private String isbn;
    private LocalDate reservedDate;
    private LocalDate expiryDate;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ReservationDto(Long id, Long userId, String isbn, LocalDate reservedDate, LocalDate expiryDate, String status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.isbn = isbn;
        this.reservedDate = reservedDate;
        this.expiryDate = expiryDate;
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

    public String getIsbn() {
        return isbn;
    }

    public LocalDate getReservedDate() {
        return reservedDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
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