package com.example.hands.on.presentation.controller.lending;

import com.example.hands.on.presentation.controller.lending.request.CreateReservationRequest;
import com.example.hands.on.presentation.controller.lending.request.FulfillReservationRequest;
import com.example.hands.on.presentation.controller.lending.response.ReservationFulfillmentResponse;
import com.example.hands.on.presentation.controller.lending.response.ReservationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse createReservation(@Valid @RequestBody CreateReservationRequest request) {
        // TODO: Implementation
        return null;
    }

    @PatchMapping("/{reservationId}/cancel")
    public ReservationResponse cancelReservation(@PathVariable Long reservationId) {
        // TODO: Implementation
        return null;
    }

    @PatchMapping("/{reservationId}/fulfill")
    public ReservationFulfillmentResponse fulfillReservation(
            @PathVariable Long reservationId,
            @Valid @RequestBody FulfillReservationRequest request) {
        // TODO: Implementation
        return null;
    }
}