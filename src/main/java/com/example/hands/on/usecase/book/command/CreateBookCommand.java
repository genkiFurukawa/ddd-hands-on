package com.example.hands.on.usecase.book.command;

import java.util.List;

public class CreateBookCommand {
    private String isbn;
    private String title;
    private List<String> authors;
    private String publisher;
    private String description;

    public CreateBookCommand(String isbn, String title, List<String> authors, String publisher, String description) {
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.description = description;
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
}