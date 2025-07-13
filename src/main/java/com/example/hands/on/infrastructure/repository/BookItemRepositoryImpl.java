package com.example.hands.on.infrastructure.repository;

import com.example.hands.on.domain.model.book.entity.BookItem;
import com.example.hands.on.domain.model.book.value.BookItemStatus;
import com.example.hands.on.domain.model.book.value.Isbn;
import com.example.hands.on.domain.repository.BookItemRepository;
import com.example.hands.on.usecase.dto.PageDto;

import java.util.List;
import java.util.Optional;

public class BookItemRepositoryImpl implements BookItemRepository {
    
    @Override
    public PageDto<BookItem> findAll(int page, int size) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public Optional<BookItem> findById(Long id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public List<BookItem> findByIsbn(Isbn isbn) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public List<BookItem> findByIsbnAndStatus(Isbn isbn, BookItemStatus status) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public List<BookItem> findAvailableByIsbn(Isbn isbn) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public long countByIsbnAndStatus(Isbn isbn, BookItemStatus status) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public long countByIsbn(Isbn isbn) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public BookItem save(BookItem bookItem) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void deleteById(Long id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public boolean canDelete(Long id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
