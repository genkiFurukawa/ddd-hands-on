package com.example.hands.on.presentation.controller.lending;

import com.example.hands.on.presentation.response.PageResponse;
import com.example.hands.on.presentation.controller.lending.request.LendBookRequest;
import com.example.hands.on.presentation.controller.lending.response.LendingResponse;
import com.example.hands.on.usecase.lending.LendingUseCase;
import com.example.hands.on.usecase.lending.command.LendBookCommand;
import com.example.hands.on.usecase.lending.dto.LendingDto;
import com.example.hands.on.usecase.dto.PageDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lendings")
public class LendingController {

    private final LendingUseCase lendingUseCase;

    public LendingController(LendingUseCase lendingUseCase) {
        this.lendingUseCase = lendingUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LendingResponse lendBook(@Valid @RequestBody LendBookRequest request) {
        LendBookCommand command = convertToLendBookCommand(request);
        LendingDto lendingDto = lendingUseCase.lendBook(command);
        return convertToLendingResponse(lendingDto);
    }

    @PatchMapping("/{lendingId}/return")
    public LendingResponse returnBook(@PathVariable Long lendingId) {
        LendingDto lendingDto = lendingUseCase.returnBook(lendingId);
        return convertToLendingResponse(lendingDto);
    }

    @GetMapping("/overdue")
    public PageResponse<LendingResponse> getOverdueLendings(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {
        PageDto<LendingDto> result = lendingUseCase.getOverdueLendings(page, size);
        return convertToPageResponse(result);
    }

    private PageResponse<LendingResponse> convertToPageResponse(PageDto<LendingDto> pageDto) {
        List<LendingResponse> content = pageDto.getContent().stream()
                .map(this::convertToLendingResponse)
                .collect(Collectors.toList());
        return new PageResponse<>(content, pageDto.getTotalElements(), pageDto.getTotalPages(), pageDto.getSize(), pageDto.getPage());
    }

    private LendingResponse convertToLendingResponse(LendingDto dto) {
        return new LendingResponse(dto.getId(), dto.getUserId(), "", dto.getBookItemId(), 
                new LendingResponse.BookSummary("", ""), dto.getLentDate(), dto.getDueDate(), 
                dto.getReturnedDate(), dto.getStatus(), 0);
    }

    private LendBookCommand convertToLendBookCommand(LendBookRequest request) {
        return new LendBookCommand(request.userId(), request.bookItemId());
    }
}