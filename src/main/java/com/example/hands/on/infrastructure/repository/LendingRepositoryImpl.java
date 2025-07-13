package com.example.hands.on.infrastructure.repository;

import com.example.hands.on.domain.model.lending.entity.Lending;
import com.example.hands.on.domain.model.lending.value.LendingStatus;
import com.example.hands.on.domain.model.book.value.Isbn;
import com.example.hands.on.domain.repository.LendingRepository;
import com.example.hands.on.usecase.dto.PageDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class LendingRepositoryImpl implements LendingRepository {
    
    @Override
    public Lending save(Lending lending) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public Optional<Lending> findById(Long id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public List<Lending> findByUserId(Long userId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public List<Lending> findByUserIdAndStatus(Long userId, LendingStatus status) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public PageDto<Lending> findByUserIdWithPagination(Long userId, LendingStatus status, int page, int size) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public PageDto<Lending> findOverdueLendings(LocalDate currentDate, int page, int size) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public PageDto<Lending> findActiveLendings(int page, int size) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public long countActiveLendingsByIsbn(Isbn isbn) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public long countActiveLendingsByUserId(Long userId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public long countOverdueLendingsByUserId(Long userId, LocalDate currentDate) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public Optional<Lending> findActiveLendingByBookItemId(Long bookItemId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
