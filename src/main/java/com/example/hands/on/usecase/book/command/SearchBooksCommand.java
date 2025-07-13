package com.example.hands.on.usecase.book.command;

public class SearchBooksCommand {
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private int page;
    private int size;

    public SearchBooksCommand(String title, String author, String publisher, String isbn, int page, int size) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.page = page;
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }
}