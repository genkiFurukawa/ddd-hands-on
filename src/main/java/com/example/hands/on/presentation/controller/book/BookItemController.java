package com.example.hands.on.presentation.controller.book;

import com.example.hands.on.presentation.controller.book.request.CreateBookItemRequest;
import com.example.hands.on.presentation.controller.book.request.UpdateBookItemRequest;
import com.example.hands.on.presentation.controller.book.response.BookItemResponse;
import com.example.hands.on.usecase.book.BookItemUseCase;
import com.example.hands.on.usecase.book.command.CreateBookItemCommand;
import com.example.hands.on.usecase.book.command.UpdateBookItemCommand;
import com.example.hands.on.usecase.book.dto.BookItemDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/book-items")
public class BookItemController {

    private final BookItemUseCase bookItemUseCase;

    public BookItemController(BookItemUseCase bookItemUseCase) {
        this.bookItemUseCase = bookItemUseCase;
    }

    @GetMapping
    public List<BookItemResponse> getAllBookItems() {
        List<BookItemDto> bookItems = bookItemUseCase.getAllBookItems();
        return bookItems.stream()
                .map(this::convertToBookItemResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BookItemResponse getBookItemById(@PathVariable Long id) {
        BookItemDto bookItemDto = bookItemUseCase.getBookItemById(id);
        return convertToBookItemResponse(bookItemDto);
    }

    @GetMapping("/book/{isbn}")
    public List<BookItemResponse> getBookItemsByIsbn(@PathVariable String isbn) {
        List<BookItemDto> bookItems = bookItemUseCase.getBookItemsByIsbn(isbn);
        return bookItems.stream()
                .map(this::convertToBookItemResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/available/{isbn}")
    public List<BookItemResponse> getAvailableBookItemsByIsbn(@PathVariable String isbn) {
        List<BookItemDto> bookItems = bookItemUseCase.getAvailableBookItemsByIsbn(isbn);
        return bookItems.stream()
                .map(this::convertToBookItemResponse)
                .collect(Collectors.toList());
    }

    @PostMapping
    public BookItemResponse createBookItem(@RequestBody CreateBookItemRequest request) {
        CreateBookItemCommand command = convertToCreateBookItemCommand(request);
        BookItemDto bookItemDto = bookItemUseCase.createBookItem(command);
        return convertToBookItemResponse(bookItemDto);
    }

    @PutMapping("/{id}")
    public BookItemResponse updateBookItem(@PathVariable Long id, @RequestBody UpdateBookItemRequest request) {
        UpdateBookItemCommand command = convertToUpdateBookItemCommand(id, request);
        BookItemDto bookItemDto = bookItemUseCase.updateBookItem(command);
        return convertToBookItemResponse(bookItemDto);
    }

    @DeleteMapping("/{id}")
    public void deleteBookItem(@PathVariable Long id) {
        bookItemUseCase.deleteBookItem(id);
    }

    private BookItemResponse convertToBookItemResponse(BookItemDto dto) {
        return new BookItemResponse(dto.getId(), dto.getIsbn(), dto.getStatus());
    }

    private CreateBookItemCommand convertToCreateBookItemCommand(CreateBookItemRequest request) {
        return new CreateBookItemCommand(request.isbn(), "");
    }

    private UpdateBookItemCommand convertToUpdateBookItemCommand(Long id, UpdateBookItemRequest request) {
        return new UpdateBookItemCommand(id, "", request.status());
    }
}