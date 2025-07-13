package com.example.hands.on.presentation.controller.lending;

import com.example.hands.on.presentation.controller.common.PageResponse;
import com.example.hands.on.presentation.controller.lending.request.LendBookRequest;
import com.example.hands.on.presentation.controller.lending.response.LendingResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/lendings")
public class LendingController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LendingResponse lendBook(@Valid @RequestBody LendBookRequest request) {
        // TODO: Implementation
        return null;
    }

    @PatchMapping("/{lendingId}/return")
    public LendingResponse returnBook(@PathVariable Long lendingId) {
        // TODO: Implementation
        return null;
    }

    @GetMapping("/overdue")
    public PageResponse<LendingResponse> getOverdueLendings(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {
        // TODO: Implementation
        return null;
    }

    @GetMapping("/active")
    public PageResponse<LendingResponse> getActiveLendings(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {
        // TODO: Implementation
        return null;
    }
}