package com.example.hands.on.usecase.book.dto;

import java.time.LocalDateTime;
import java.util.List;

public class BookDto {
    private String isbn;
    private String title;
    private List<String> authors;
    private String publisher;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BookDto(String isbn, String title, List<String> authors, String publisher, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}