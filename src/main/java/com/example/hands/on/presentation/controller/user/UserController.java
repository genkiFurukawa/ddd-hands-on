package com.example.hands.on.presentation.controller.user;

import com.example.hands.on.presentation.response.PageResponse;
import com.example.hands.on.presentation.controller.lending.response.LendingResponse;
import com.example.hands.on.presentation.controller.lending.response.ReservationResponse;
import com.example.hands.on.presentation.controller.user.request.CreateUserRequest;
import com.example.hands.on.presentation.controller.user.request.UpdateUserRequest;
import com.example.hands.on.presentation.controller.user.response.UserResponse;
import com.example.hands.on.usecase.user.UserUseCase;
import com.example.hands.on.usecase.user.command.CreateUserCommand;
import com.example.hands.on.usecase.user.command.SearchUsersCommand;
import com.example.hands.on.usecase.user.command.UpdateUserCommand;
import com.example.hands.on.usecase.user.dto.UserDto;
import com.example.hands.on.usecase.lending.dto.LendingDto;
import com.example.hands.on.usecase.lending.dto.ReservationDto;
import com.example.hands.on.usecase.common.dto.PageDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserUseCase userUseCase;

    public UserController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @GetMapping
    public PageResponse<UserResponse> getAllUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {
        SearchUsersCommand command = new SearchUsersCommand(name, email, status, page, size);
        PageDto<UserDto> result = userUseCase.searchUsers(command);
        return convertToUserPageResponse(result);
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        UserDto userDto = userUseCase.getUserById(id);
        return convertToUserResponse(userDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@Valid @RequestBody CreateUserRequest request) {
        CreateUserCommand command = convertToCreateUserCommand(request);
        UserDto userDto = userUseCase.createUser(command);
        return convertToUserResponse(userDto);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
        UpdateUserCommand command = convertToUpdateUserCommand(id, request);
        UserDto userDto = userUseCase.updateUser(command);
        return convertToUserResponse(userDto);
    }

    @GetMapping("/{userId}/lendings")
    public PageResponse<LendingResponse> getUserLendings(
            @PathVariable Long userId,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {
        PageDto<LendingDto> result = userUseCase.getUserLendings(userId, status, page, size);
        return convertToLendingPageResponse(result);
    }

    @GetMapping("/{userId}/reservations")
    public PageResponse<ReservationResponse> getUserReservations(
            @PathVariable Long userId,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {
        PageDto<ReservationDto> result = userUseCase.getUserReservations(userId, status, page, size);
        return convertToReservationPageResponse(result);
    }

    private PageResponse<UserResponse> convertToUserPageResponse(PageDto<UserDto> pageDto) {
        List<UserResponse> content = pageDto.getContent().stream()
                .map(this::convertToUserResponse)
                .collect(Collectors.toList());
        return new PageResponse<>(content, pageDto.getTotalElements(), pageDto.getTotalPages(), pageDto.getSize(), pageDto.getPage());
    }

    private PageResponse<LendingResponse> convertToLendingPageResponse(PageDto<LendingDto> pageDto) {
        List<LendingResponse> content = pageDto.getContent().stream()
                .map(this::convertToLendingResponse)
                .collect(Collectors.toList());
        return new PageResponse<>(content, pageDto.getTotalElements(), pageDto.getTotalPages(), pageDto.getSize(), pageDto.getPage());
    }

    private PageResponse<ReservationResponse> convertToReservationPageResponse(PageDto<ReservationDto> pageDto) {
        List<ReservationResponse> content = pageDto.getContent().stream()
                .map(this::convertToReservationResponse)
                .collect(Collectors.toList());
        return new PageResponse<>(content, pageDto.getTotalElements(), pageDto.getTotalPages(), pageDto.getSize(), pageDto.getPage());
    }

    private UserResponse convertToUserResponse(UserDto dto) {
        return new UserResponse(dto.getId(), dto.getName(), dto.getEmail(), dto.getCreatedAt().toLocalDate(), dto.getStatus());
    }

    private LendingResponse convertToLendingResponse(LendingDto dto) {
        return new LendingResponse(dto.getId(), dto.getUserId(), "", dto.getBookItemId(), 
                new LendingResponse.BookSummary("", ""), dto.getLentDate(), dto.getDueDate(), 
                dto.getReturnedDate(), dto.getStatus(), 0);
    }

    private ReservationResponse convertToReservationResponse(ReservationDto dto) {
        return new ReservationResponse(dto.getId(), dto.getUserId(), dto.getIsbn(), 
                new ReservationResponse.BookSummary(dto.getIsbn(), ""), dto.getReservedDate(), 
                dto.getExpiryDate(), dto.getStatus());
    }

    private CreateUserCommand convertToCreateUserCommand(CreateUserRequest request) {
        return new CreateUserCommand(request.name(), request.email());
    }

    private UpdateUserCommand convertToUpdateUserCommand(Long id, UpdateUserRequest request) {
        return new UpdateUserCommand(id, request.name(), request.email(), request.status());
    }
}