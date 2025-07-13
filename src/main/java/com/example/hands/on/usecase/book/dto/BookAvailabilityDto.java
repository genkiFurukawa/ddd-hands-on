package com.example.hands.on.usecase.book.dto;

public class BookAvailabilityDto {
    private String isbn;
    private int totalCopies;
    private int availableCopies;
    private int lentCopies;
    private int reservedCopies;

    public BookAvailabilityDto(String isbn, int totalCopies, int availableCopies, int lentCopies, int reservedCopies) {
        this.isbn = isbn;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
        this.lentCopies = lentCopies;
        this.reservedCopies = reservedCopies;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public int getLentCopies() {
        return lentCopies;
    }

    public int getReservedCopies() {
        return reservedCopies;
    }
}