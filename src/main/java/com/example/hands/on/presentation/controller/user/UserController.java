package com.example.hands.on.presentation.controller.user;

import com.example.hands.on.presentation.controller.common.PageResponse;
import com.example.hands.on.presentation.controller.lending.response.LendingResponse;
import com.example.hands.on.presentation.controller.lending.response.ReservationResponse;
import com.example.hands.on.presentation.controller.user.request.CreateUserRequest;
import com.example.hands.on.presentation.controller.user.request.UpdateUserRequest;
import com.example.hands.on.presentation.controller.user.response.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping
    public PageResponse<UserResponse> getAllUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {
        // TODO: Implementation
        return null;
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        // TODO: Implementation
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@Valid @RequestBody CreateUserRequest request) {
        // TODO: Implementation
        return null;
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
        // TODO: Implementation
        return null;
    }

    @GetMapping("/{userId}/lendings")
    public PageResponse<LendingResponse> getUserLendings(
            @PathVariable Long userId,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {
        // TODO: Implementation
        return null;
    }

    @GetMapping("/{userId}/reservations")
    public PageResponse<ReservationResponse> getUserReservations(
            @PathVariable Long userId,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {
        // TODO: Implementation
        return null;
    }
}