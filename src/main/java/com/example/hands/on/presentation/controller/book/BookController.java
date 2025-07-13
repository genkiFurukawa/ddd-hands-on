package com.example.hands.on.presentation.controller.book;

import com.example.hands.on.presentation.controller.book.request.CreateBookRequest;
import com.example.hands.on.presentation.controller.book.response.BookAvailabilityResponse;
import com.example.hands.on.presentation.controller.book.response.BookItemResponse;
import com.example.hands.on.presentation.controller.book.response.BookResponse;
import com.example.hands.on.presentation.controller.common.PageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @GetMapping
    public PageResponse<BookResponse> getAllBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String publisher,
            @RequestParam(required = false) String isbn,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {
        // TODO: Implementation
        return null;
    }

    @GetMapping("/{isbn}")
    public BookResponse getBookByIsbn(@PathVariable String isbn) {
        // TODO: Implementation
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponse createBook(@Valid @RequestBody CreateBookRequest request) {
        // TODO: Implementation
        return null;
    }

    @GetMapping("/{isbn}/items")
    public List<BookItemResponse> getBookItems(
            @PathVariable String isbn,
            @RequestParam(required = false) String status) {
        // TODO: Implementation
        return null;
    }

    @GetMapping("/{isbn}/availability")
    public BookAvailabilityResponse getBookAvailability(@PathVariable String isbn) {
        // TODO: Implementation
        return null;
    }
}