package com.example.hands.on.domain.model.book.entity;

import com.example.hands.on.domain.model.book.value.BookItemStatus;
import com.example.hands.on.domain.model.book.value.Isbn;

public record BookItem(long id, Isbn isbn, BookItemStatus status) {
}