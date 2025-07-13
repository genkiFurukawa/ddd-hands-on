package com.example.hands.on.usecase.lending;

import com.example.hands.on.usecase.lending.command.CreateReservationCommand;
import com.example.hands.on.usecase.lending.command.FulfillReservationCommand;
import com.example.hands.on.usecase.lending.dto.ReservationDto;
import com.example.hands.on.usecase.lending.dto.ReservationFulfillmentDto;
import org.springframework.stereotype.Service;

@Service
public class ReservationUseCase {

    public ReservationDto createReservation(CreateReservationCommand command) {
        // TODO: Implement reservation creation logic
        // 1. Validate command parameters
        // 2. Check if user exists and is active
        // 3. Check if book exists (by ISBN)
        // 4. Check if there are available copies
        // 5. Check if user already has an active reservation for this book
        // 6. Check user's reservation limits
        // 7. Create reservation domain entity
        // 8. Set reservation dates (reserved date = today, expiry date = today + reservation period)
        // 9. Set status to ACTIVE
        // 10. Save to repository
        // 11. Convert saved entity to DTO
        // 12. Return reservation DTO
        return null;
    }

    public ReservationDto cancelReservation(Long reservationId) {
        // TODO: Implement reservation cancellation logic
        // 1. Validate reservation ID
        // 2. Get existing reservation by ID
        // 3. Throw exception if reservation not found
        // 4. Check if reservation is active (can be cancelled)
        // 5. Update reservation status to CANCELLED
        // 6. Save updated entity to repository
        // 7. Convert updated entity to DTO
        // 8. Return reservation DTO
        return null;
    }

    public ReservationFulfillmentDto fulfillReservation(FulfillReservationCommand command) {
        // TODO: Implement reservation fulfillment logic
        // 1. Validate command parameters
        // 2. Get existing reservation by ID
        // 3. Throw exception if reservation not found or not active
        // 4. Check if book item exists and is available
        // 5. Check if book item matches the reserved book (ISBN)
        // 6. Update reservation status to FULFILLED
        // 7. Create lending entity for the reserved book
        // 8. Update book item status to LENT
        // 9. Save all updated entities to repositories
        // 10. Convert entities to DTOs
        // 11. Return reservation fulfillment DTO containing both reservation and lending
        return null;
    }
}