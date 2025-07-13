package com.example.hands.on.domain.model.book.entity;

import com.example.hands.on.domain.model.book.value.Isbn;

import java.util.List;

public record Book(Isbn isbn, String title, List<Author> authors, Publisher publisher) {
}