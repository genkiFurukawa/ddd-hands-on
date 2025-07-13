package com.example.hands.on.presentation.controller.user;

import com.example.hands.on.presentation.controller.user.request.CreateUserRequest;
import com.example.hands.on.presentation.controller.user.request.UpdateUserRequest;
import com.example.hands.on.usecase.user.UserUseCase;
import com.example.hands.on.usecase.user.command.CreateUserCommand;
import com.example.hands.on.usecase.user.command.SearchUsersCommand;
import com.example.hands.on.usecase.user.command.UpdateUserCommand;
import com.example.hands.on.usecase.user.dto.UserDto;
import com.example.hands.on.usecase.lending.dto.LendingDto;
import com.example.hands.on.usecase.lending.dto.ReservationDto;
import com.example.hands.on.usecase.dto.PageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserUseCase userUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void すべてのユーザーを取得できること() throws Exception {
        List<UserDto> users = Arrays.asList(
                createUserDto(1L, "山田太郎", "yamada@example.com", "ACTIVE"),
                createUserDto(2L, "佐藤花子", "sato@example.com", "ACTIVE")
        );
        PageDto<UserDto> pageDto = new PageDto<>(users, 0, 20, 2L, 1, true, true);
        when(userUseCase.searchUsers(any(SearchUsersCommand.class))).thenReturn(pageDto);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.totalElements").value(2));
    }

    @Test
    void 検索条件を指定してユーザーを取得できること() throws Exception {
        List<UserDto> users = Arrays.asList(
                createUserDto(1L, "山田太郎", "yamada@example.com", "ACTIVE")
        );
        PageDto<UserDto> pageDto = new PageDto<>(users, 0, 10, 1L, 1, true, true);
        when(userUseCase.searchUsers(any(SearchUsersCommand.class))).thenReturn(pageDto);

        mockMvc.perform(get("/api/users")
                        .param("name", "山田")
                        .param("email", "yamada")
                        .param("status", "ACTIVE")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].name").value("山田太郎"));
    }

    @Test
    void ページングパラメータのバリデーションが機能すること() throws Exception {
        mockMvc.perform(get("/api/users")
                        .param("page", "-1")
                        .param("size", "101"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void IDで特定のユーザーを取得できること() throws Exception {
        UserDto userDto = createUserDto(1L, "山田太郎", "yamada@example.com", "ACTIVE");
        when(userUseCase.getUserById(1L)).thenReturn(userDto);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("山田太郎"))
                .andExpect(jsonPath("$.email").value("yamada@example.com"));
    }

    @Test
    void ユーザーの貸出履歴を取得できること() throws Exception {
        List<LendingDto> lendings = Arrays.asList(
                createLendingDto(1L, 1L, 1L, "ACTIVE"),
                createLendingDto(2L, 1L, 2L, "RETURNED")
        );
        PageDto<LendingDto> pageDto = new PageDto<>(lendings, 0, 20, 2L, 1, true, true);
        when(userUseCase.getUserLendings(eq(1L), any(), anyInt(), anyInt())).thenReturn(pageDto);

        mockMvc.perform(get("/api/users/1/lendings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].status").value("ACTIVE"));
    }

    @Test
    void ユーザーの予約履歴を取得できること() throws Exception {
        List<ReservationDto> reservations = Arrays.asList(
                createReservationDto(1L, 1L, "978-4-123456-78-0", "ACTIVE"),
                createReservationDto(2L, 1L, "978-4-123456-78-1", "FULFILLED")
        );
        PageDto<ReservationDto> pageDto = new PageDto<>(reservations, 0, 20, 2L, 1, true, true);
        when(userUseCase.getUserReservations(eq(1L), any(), anyInt(), anyInt())).thenReturn(pageDto);

        mockMvc.perform(get("/api/users/1/reservations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].status").value("ACTIVE"));
    }

    @Test
    void ステータスを指定して貸出履歴を取得できること() throws Exception {
        List<LendingDto> activeLendings = Arrays.asList(
                createLendingDto(1L, 1L, 1L, "ACTIVE")
        );
        PageDto<LendingDto> pageDto = new PageDto<>(activeLendings, 0, 20, 1L, 1, true, true);
        when(userUseCase.getUserLendings(eq(1L), eq("ACTIVE"), anyInt(), anyInt())).thenReturn(pageDto);

        mockMvc.perform(get("/api/users/1/lendings")
                        .param("status", "ACTIVE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].status").value("ACTIVE"));
    }

    @Test
    void ステータスを指定して予約履歴を取得できること() throws Exception {
        List<ReservationDto> activeReservations = Arrays.asList(
                createReservationDto(1L, 1L, "978-4-123456-78-0", "ACTIVE")
        );
        PageDto<ReservationDto> pageDto = new PageDto<>(activeReservations, 0, 20, 1L, 1, true, true);
        when(userUseCase.getUserReservations(eq(1L), eq("ACTIVE"), anyInt(), anyInt())).thenReturn(pageDto);

        mockMvc.perform(get("/api/users/1/reservations")
                        .param("status", "ACTIVE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].status").value("ACTIVE"));
    }

    @Test
    void 存在しないユーザーIDでのアクセスが適切にハンドリングされること() throws Exception {
        when(userUseCase.getUserById(999L)).thenThrow(new RuntimeException("User not found"));

        mockMvc.perform(get("/api/users/999"))
                .andExpect(status().isInternalServerError());
    }

    private UserDto createUserDto(Long id, String name, String email, String status) {
        LocalDateTime now = LocalDateTime.now();
        return new UserDto(id, name, email, status, now, now);
    }

    private LendingDto createLendingDto(Long id, Long userId, Long bookItemId, String status) {
        LocalDateTime now = LocalDateTime.now();
        return new LendingDto(id, userId, bookItemId, LocalDate.now(), LocalDate.now().plusDays(14), null, status, now, now);
    }

    private ReservationDto createReservationDto(Long id, Long userId, String isbn, String status) {
        LocalDateTime now = LocalDateTime.now();
        return new ReservationDto(id, userId, isbn, LocalDate.now(), LocalDate.now().plusDays(7), status, now, now);
    }
}