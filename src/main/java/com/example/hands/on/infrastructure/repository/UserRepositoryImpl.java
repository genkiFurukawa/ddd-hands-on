package com.example.hands.on.infrastructure.repository;

import com.example.hands.on.domain.model.user.entity.User;
import com.example.hands.on.domain.model.user.value.UserStatus;
import com.example.hands.on.domain.repository.UserRepository;
import com.example.hands.on.usecase.user.command.SearchUsersCommand;
import com.example.hands.on.usecase.dto.PageDto;

import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    
    @Override
    public PageDto<User> searchUsers(SearchUsersCommand command, int page, int size) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public Optional<User> findById(Long id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public Optional<User> findByEmail(String email) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public boolean existsByEmail(String email) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public PageDto<User> findByStatus(UserStatus status, int page, int size) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public User save(User user) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public PageDto<User> findAll(int page, int size) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
