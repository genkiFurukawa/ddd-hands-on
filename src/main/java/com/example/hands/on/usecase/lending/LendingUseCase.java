package com.example.hands.on.usecase.lending;

import com.example.hands.on.usecase.common.dto.PageDto;
import com.example.hands.on.usecase.lending.command.LendBookCommand;
import com.example.hands.on.usecase.lending.dto.LendingDto;
import org.springframework.stereotype.Service;

@Service
public class LendingUseCase {

    public LendingDto lendBook(LendBookCommand command) {
        // TODO: Implement book lending logic
        // 1. Validate command parameters
        // 2. Check if user exists and is active
        // 3. Check if book item exists and is available
        // 4. Check user's lending limits (max books, overdue books)
        // 5. Create lending domain entity
        // 6. Set lending dates (lent date = today, due date = today + lending period)
        // 7. Update book item status to LENT
        // 8. Save lending and book item to repositories
        // 9. Convert saved entity to DTO
        // 10. Return lending DTO
        return null;
    }

    public LendingDto returnBook(Long lendingId) {
        // TODO: Implement book return logic
        // 1. Validate lending ID
        // 2. Get existing lending by ID
        // 3. Throw exception if lending not found
        // 4. Check if lending is active (not already returned)
        // 5. Set return date to today
        // 6. Update lending status to RETURNED
        // 7. Update book item status to AVAILABLE
        // 8. Calculate and apply late fees if overdue
        // 9. Save updated entities to repositories
        // 10. Convert updated entity to DTO
        // 11. Return lending DTO
        return null;
    }

    public PageDto<LendingDto> getOverdueLendings(int page, int size) {
        // TODO: Implement get overdue lendings logic
        // 1. Validate pagination parameters
        // 2. Calculate current date
        // 3. Query lendings where due date < current date and status = ACTIVE
        // 4. Implement pagination
        // 5. Convert domain entities to DTOs
        // 6. Return paginated overdue lending results
        return null;
    }

    public PageDto<LendingDto> getActiveLendings(int page, int size) {
        // TODO: Implement get active lendings logic
        // 1. Validate pagination parameters
        // 2. Query lendings where status = ACTIVE
        // 3. Implement pagination
        // 4. Convert domain entities to DTOs
        // 5. Return paginated active lending results
        return null;
    }
}