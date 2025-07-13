package com.example.hands.on.presentation.controller.lending;

import com.example.hands.on.presentation.controller.lending.request.CreateReservationRequest;
import com.example.hands.on.presentation.controller.lending.request.FulfillReservationRequest;
import com.example.hands.on.presentation.controller.lending.response.ReservationFulfillmentResponse;
import com.example.hands.on.presentation.controller.lending.response.ReservationResponse;
import com.example.hands.on.usecase.lending.ReservationUseCase;
import com.example.hands.on.usecase.lending.command.CreateReservationCommand;
import com.example.hands.on.usecase.lending.command.FulfillReservationCommand;
import com.example.hands.on.usecase.lending.dto.LendingDto;
import com.example.hands.on.usecase.lending.dto.ReservationDto;
import com.example.hands.on.usecase.lending.dto.ReservationFulfillmentDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReservationUseCase reservationUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 予約を作成できること() throws Exception {
        CreateReservationRequest request = new CreateReservationRequest(1L, "978-4-123456-78-0");
        ReservationDto reservationDto = createReservationDto(1L, 1L, "978-4-123456-78-0", "ACTIVE");
        when(reservationUseCase.createReservation(any(CreateReservationCommand.class))).thenReturn(reservationDto);

        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.isbn").value("978-4-123456-78-0"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void バリデーションエラーで予約作成が失敗すること() throws Exception {
        CreateReservationRequest invalidRequest = new CreateReservationRequest(null, "");

        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 予約をキャンセルできること() throws Exception {
        ReservationDto cancelledDto = createReservationDto(1L, 1L, "978-4-123456-78-0", "CANCELLED");
        when(reservationUseCase.cancelReservation(1L)).thenReturn(cancelledDto);

        mockMvc.perform(patch("/api/reservations/1/cancel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("CANCELLED"));
    }

    @Test
    void 予約を実現できること() throws Exception {
        FulfillReservationRequest request = new FulfillReservationRequest(LocalDate.now().plusDays(14));
        ReservationDto reservationDto = createReservationDto(1L, 1L, "978-4-123456-78-0", "FULFILLED");
        LendingDto lendingDto = createLendingDto(1L, 1L, 1L, "ACTIVE");
        ReservationFulfillmentDto fulfillmentDto = new ReservationFulfillmentDto(reservationDto, lendingDto);
        when(reservationUseCase.fulfillReservation(any(FulfillReservationCommand.class))).thenReturn(fulfillmentDto);

        mockMvc.perform(patch("/api/reservations/1/fulfill")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lending.id").value(1))
                .andExpect(jsonPath("$.lending.status").value("ACTIVE"))
                .andExpect(jsonPath("$.reservation.id").value(1))
                .andExpect(jsonPath("$.reservation.status").value("FULFILLED"));
    }

    @Test
    void バリデーションエラーで予約実現が失敗すること() throws Exception {
        FulfillReservationRequest invalidRequest = new FulfillReservationRequest(null);

        mockMvc.perform(patch("/api/reservations/1/fulfill")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 存在しない予約のキャンセルが適切にハンドリングされること() throws Exception {
        when(reservationUseCase.cancelReservation(999L)).thenThrow(new RuntimeException("Reservation not found"));

        mockMvc.perform(patch("/api/reservations/999/cancel"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void 存在しない予約の実現が適切にハンドリングされること() throws Exception {
        FulfillReservationRequest request = new FulfillReservationRequest(LocalDate.now().plusDays(14));
        when(reservationUseCase.fulfillReservation(any(FulfillReservationCommand.class)))
                .thenThrow(new RuntimeException("Reservation not found"));

        mockMvc.perform(patch("/api/reservations/999/fulfill")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void JSONリクエストボディなしで予約実現が失敗すること() throws Exception {
        mockMvc.perform(patch("/api/reservations/1/fulfill")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private ReservationDto createReservationDto(Long id, Long userId, String isbn, String status) {
        LocalDateTime now = LocalDateTime.now();
        return new ReservationDto(id, userId, isbn, LocalDate.now(), LocalDate.now().plusDays(7), status, now, now);
    }

    private LendingDto createLendingDto(Long id, Long userId, Long bookItemId, String status) {
        LocalDateTime now = LocalDateTime.now();
        return new LendingDto(id, userId, bookItemId, LocalDate.now(), LocalDate.now().plusDays(14), null, status, now, now);
    }
}