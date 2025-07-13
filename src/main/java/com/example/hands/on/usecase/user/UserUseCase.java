package com.example.hands.on.usecase.user;

import com.example.hands.on.usecase.dto.PageDto;
import com.example.hands.on.usecase.lending.dto.LendingDto;
import com.example.hands.on.usecase.lending.dto.ReservationDto;
import com.example.hands.on.usecase.user.command.CreateUserCommand;
import com.example.hands.on.usecase.user.command.SearchUsersCommand;
import com.example.hands.on.usecase.user.command.UpdateUserCommand;
import com.example.hands.on.usecase.user.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserUseCase {

    public PageDto<UserDto> searchUsers(SearchUsersCommand command) {
        // TODO: Implement user search logic
        // 1. Validate search parameters
        // 2. Apply filters (name, email, status)
        // 3. Implement pagination
        // 4. Query repository with filters and pagination
        // 5. Convert domain entities to DTOs
        // 6. Return paginated results
        return null;
    }

    public UserDto getUserById(Long id) {
        // TODO: Implement get user by ID logic
        // 1. Validate ID
        // 2. Query repository by ID
        // 3. Throw exception if user not found
        // 4. Convert domain entity to DTO
        // 5. Return user DTO
        return null;
    }

    public UserDto createUser(CreateUserCommand command) {
        // TODO: Implement user creation logic
        // 1. Validate command parameters
        // 2. Check if user with same email already exists
        // 3. Create user domain entity
        // 4. Set initial status to ACTIVE
        // 5. Save to repository
        // 6. Convert saved entity to DTO
        // 7. Return user DTO
        return null;
    }

    public UserDto updateUser(UpdateUserCommand command) {
        // TODO: Implement user update logic
        // 1. Validate command parameters
        // 2. Get existing user by ID
        // 3. Throw exception if user not found
        // 4. Update allowed fields (name, email, status)
        // 5. Validate email uniqueness if changed
        // 6. Save updated entity to repository
        // 7. Convert updated entity to DTO
        // 8. Return updated user DTO
        return null;
    }

    public PageDto<LendingDto> getUserLendings(Long userId, String status, int page, int size) {
        // TODO: Implement get user lendings logic
        // 1. Validate user ID and pagination parameters
        // 2. Check if user exists
        // 3. Query lendings by user ID
        // 4. Apply status filter if provided
        // 5. Implement pagination
        // 6. Convert domain entities to DTOs
        // 7. Return paginated lending results
        return null;
    }

    public PageDto<ReservationDto> getUserReservations(Long userId, String status, int page, int size) {
        // TODO: Implement get user reservations logic
        // 1. Validate user ID and pagination parameters
        // 2. Check if user exists
        // 3. Query reservations by user ID
        // 4. Apply status filter if provided
        // 5. Implement pagination
        // 6. Convert domain entities to DTOs
        // 7. Return paginated reservation results
        return null;
    }
}