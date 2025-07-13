package com.example.hands.on.presentation.controller.lending;

import com.example.hands.on.presentation.controller.lending.request.CreateReservationRequest;
import com.example.hands.on.presentation.controller.lending.request.FulfillReservationRequest;
import com.example.hands.on.presentation.controller.lending.response.LendingResponse;
import com.example.hands.on.presentation.controller.lending.response.ReservationFulfillmentResponse;
import com.example.hands.on.presentation.controller.lending.response.ReservationResponse;
import com.example.hands.on.usecase.lending.ReservationUseCase;
import com.example.hands.on.usecase.lending.command.CreateReservationCommand;
import com.example.hands.on.usecase.lending.command.FulfillReservationCommand;
import com.example.hands.on.usecase.lending.dto.ReservationDto;
import com.example.hands.on.usecase.lending.dto.ReservationFulfillmentDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationUseCase reservationUseCase;

    public ReservationController(ReservationUseCase reservationUseCase) {
        this.reservationUseCase = reservationUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse createReservation(@Valid @RequestBody CreateReservationRequest request) {
        CreateReservationCommand command = convertToCreateReservationCommand(request);
        ReservationDto reservationDto = reservationUseCase.createReservation(command);
        return convertToReservationResponse(reservationDto);
    }

    @PatchMapping("/{reservationId}/cancel")
    public ReservationResponse cancelReservation(@PathVariable Long reservationId) {
        ReservationDto reservationDto = reservationUseCase.cancelReservation(reservationId);
        return convertToReservationResponse(reservationDto);
    }

    @PatchMapping("/{reservationId}/fulfill")
    public ReservationFulfillmentResponse fulfillReservation(
            @PathVariable Long reservationId,
            @Valid @RequestBody FulfillReservationRequest request) {
        FulfillReservationCommand command = convertToFulfillReservationCommand(reservationId, request);
        ReservationFulfillmentDto fulfillmentDto = reservationUseCase.fulfillReservation(command);
        return convertToReservationFulfillmentResponse(fulfillmentDto);
    }

    private ReservationResponse convertToReservationResponse(ReservationDto dto) {
        return new ReservationResponse(dto.getId(), dto.getUserId(), dto.getIsbn(), 
                new ReservationResponse.BookSummary(dto.getIsbn(), ""), dto.getReservedDate(), 
                dto.getExpiryDate(), dto.getStatus());
    }

    private ReservationFulfillmentResponse convertToReservationFulfillmentResponse(ReservationFulfillmentDto dto) {
        LendingResponse lending = new LendingResponse(dto.getLending().getId(), dto.getLending().getUserId(), "", 
                dto.getLending().getBookItemId(), new LendingResponse.BookSummary("", ""), 
                dto.getLending().getLentDate(), dto.getLending().getDueDate(), 
                dto.getLending().getReturnedDate(), dto.getLending().getStatus(), 0);
        ReservationFulfillmentResponse.ReservationSummary reservation = 
                new ReservationFulfillmentResponse.ReservationSummary(dto.getReservation().getId(), 
                        dto.getReservation().getStatus());
        return new ReservationFulfillmentResponse(lending, reservation);
    }

    private CreateReservationCommand convertToCreateReservationCommand(CreateReservationRequest request) {
        return new CreateReservationCommand(request.userId(), request.bookIsbn());
    }

    private FulfillReservationCommand convertToFulfillReservationCommand(Long reservationId, FulfillReservationRequest request) {
        return new FulfillReservationCommand(reservationId, 1L);
    }
}