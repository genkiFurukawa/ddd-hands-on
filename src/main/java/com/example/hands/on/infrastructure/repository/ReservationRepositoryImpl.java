package com.example.hands.on.infrastructure.repository;

import com.example.hands.on.domain.model.lending.entity.Reservation;
import com.example.hands.on.domain.model.lending.value.ReservationStatus;
import com.example.hands.on.domain.model.book.value.Isbn;
import com.example.hands.on.domain.repository.ReservationRepository;
import com.example.hands.on.usecase.dto.PageDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ReservationRepositoryImpl implements ReservationRepository {
    
    @Override
    public Reservation save(Reservation reservation) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public Optional<Reservation> findById(Long id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public List<Reservation> findByUserId(Long userId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public List<Reservation> findByUserIdAndStatus(Long userId, ReservationStatus status) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public PageDto<Reservation> findByUserIdWithPagination(Long userId, ReservationStatus status, int page, int size) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public Optional<Reservation> findActiveReservationByUserIdAndIsbn(Long userId, Isbn isbn) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public List<Reservation> findActiveReservationsByIsbnOrderByReservedDate(Isbn isbn) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public long countActiveReservationsByIsbn(Isbn isbn) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public long countActiveReservationsByUserId(Long userId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public PageDto<Reservation> findExpiredReservations(LocalDate currentDate, int page, int size) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public PageDto<Reservation> findReadyReservations(int page, int size) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public Optional<Reservation> findNextWaitingReservationByIsbn(Isbn isbn) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
