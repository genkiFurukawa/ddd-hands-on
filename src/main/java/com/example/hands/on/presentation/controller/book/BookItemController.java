package com.example.hands.on.presentation.controller.book;

import com.example.hands.on.presentation.controller.book.request.CreateBookItemRequest;
import com.example.hands.on.presentation.controller.book.request.UpdateBookItemRequest;
import com.example.hands.on.presentation.controller.book.response.BookItemResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book-items")
public class BookItemController {

    @GetMapping
    public List<BookItemResponse> getAllBookItems() {
        // TODO: Implementation
        return null;
    }

    @GetMapping("/{id}")
    public BookItemResponse getBookItemById(@PathVariable Long id) {
        // TODO: Implementation
        return null;
    }

    @GetMapping("/book/{isbn}")
    public List<BookItemResponse> getBookItemsByIsbn(@PathVariable String isbn) {
        // TODO: Implementation
        return null;
    }

    @GetMapping("/available/{isbn}")
    public List<BookItemResponse> getAvailableBookItemsByIsbn(@PathVariable String isbn) {
        // TODO: Implementation
        return null;
    }

    @PostMapping
    public BookItemResponse createBookItem(@RequestBody CreateBookItemRequest request) {
        // TODO: Implementation
        return null;
    }

    @PutMapping("/{id}")
    public BookItemResponse updateBookItem(@PathVariable Long id, @RequestBody UpdateBookItemRequest request) {
        // TODO: Implementation
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteBookItem(@PathVariable Long id) {
        // TODO: Implementation
    }
}