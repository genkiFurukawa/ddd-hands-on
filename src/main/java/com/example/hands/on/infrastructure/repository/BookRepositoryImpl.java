package com.example.hands.on.infrastructure.repository;

import com.example.hands.on.domain.model.book.entity.Book;
import com.example.hands.on.domain.model.book.value.Isbn;
import com.example.hands.on.domain.repository.BookRepository;
import com.example.hands.on.usecase.book.command.SearchBooksCommand;
import com.example.hands.on.usecase.dto.PageDto;

import java.util.Optional;

public class BookRepositoryImpl implements BookRepository {
    
    @Override
    public PageDto<Book> searchBooks(SearchBooksCommand command, int page, int size) {
        // TODO: 実装が必要
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public Optional<Book> findByIsbn(Isbn isbn) {
        // TODO: 実装が必要
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public boolean existsByIsbn(Isbn isbn) {
        // TODO: 実装が必要
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public Book save(Book book) {
        // TODO: 実装が必要
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
