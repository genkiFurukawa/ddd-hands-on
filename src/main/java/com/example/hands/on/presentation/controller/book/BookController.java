package com.example.hands.on.presentation.controller.book;

import com.example.hands.on.presentation.controller.book.request.CreateBookRequest;
import com.example.hands.on.presentation.controller.book.response.BookAvailabilityResponse;
import com.example.hands.on.presentation.controller.book.response.BookItemResponse;
import com.example.hands.on.presentation.controller.book.response.BookResponse;
import com.example.hands.on.presentation.response.PageResponse;
import com.example.hands.on.usecase.book.BookUseCase;
import com.example.hands.on.usecase.book.command.CreateBookCommand;
import com.example.hands.on.usecase.book.command.SearchBooksCommand;
import com.example.hands.on.usecase.book.dto.BookAvailabilityDto;
import com.example.hands.on.usecase.book.dto.BookDto;
import com.example.hands.on.usecase.book.dto.BookItemDto;
import com.example.hands.on.usecase.common.dto.PageDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookUseCase bookUseCase;

    public BookController(BookUseCase bookUseCase) {
        this.bookUseCase = bookUseCase;
    }

    @GetMapping
    public PageResponse<BookResponse> getAllBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String publisher,
            @RequestParam(required = false) String isbn,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {
        SearchBooksCommand command = new SearchBooksCommand(title, author, publisher, isbn, page, size);
        PageDto<BookDto> result = bookUseCase.searchBooks(command);
        return convertToPageResponse(result);
    }

    @GetMapping("/{isbn}")
    public BookResponse getBookByIsbn(@PathVariable String isbn) {
        BookDto bookDto = bookUseCase.getBookByIsbn(isbn);
        return convertToBookResponse(bookDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponse createBook(@Valid @RequestBody CreateBookRequest request) {
        CreateBookCommand command = convertToCreateBookCommand(request);
        BookDto bookDto = bookUseCase.createBook(command);
        return convertToBookResponse(bookDto);
    }

    @GetMapping("/{isbn}/items")
    public List<BookItemResponse> getBookItems(
            @PathVariable String isbn,
            @RequestParam(required = false) String status) {
        List<BookItemDto> bookItems = bookUseCase.getBookItems(isbn, status);
        return bookItems.stream()
                .map(this::convertToBookItemResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{isbn}/availability")
    public BookAvailabilityResponse getBookAvailability(@PathVariable String isbn) {
        BookAvailabilityDto availabilityDto = bookUseCase.getBookAvailability(isbn);
        return convertToBookAvailabilityResponse(availabilityDto);
    }

    private PageResponse<BookResponse> convertToPageResponse(PageDto<BookDto> pageDto) {
        List<BookResponse> content = pageDto.getContent().stream()
                .map(this::convertToBookResponse)
                .collect(Collectors.toList());
        return new PageResponse<>(content, pageDto.getTotalElements(), pageDto.getTotalPages(), pageDto.getSize(), pageDto.getPage());
    }

    private BookResponse convertToBookResponse(BookDto dto) {
        List<BookResponse.AuthorResponse> authors = dto.getAuthors().stream()
                .map(BookResponse.AuthorResponse::new)
                .collect(Collectors.toList());
        BookResponse.PublisherResponse publisher = new BookResponse.PublisherResponse(dto.getPublisher());
        return new BookResponse(dto.getIsbn(), dto.getTitle(), authors, publisher);
    }

    private BookItemResponse convertToBookItemResponse(BookItemDto dto) {
        return new BookItemResponse(dto.getId(), dto.getIsbn(), dto.getStatus());
    }

    private BookAvailabilityResponse convertToBookAvailabilityResponse(BookAvailabilityDto dto) {
        return new BookAvailabilityResponse(dto.getIsbn(), dto.getTotalCopies(), dto.getAvailableCopies(), 
                dto.getLentCopies(), dto.getReservedCopies(), 0, 0);
    }

    private CreateBookCommand convertToCreateBookCommand(CreateBookRequest request) {
        List<String> authors = request.authors().stream()
                .map(CreateBookRequest.AuthorRequest::name)
                .collect(Collectors.toList());
        return new CreateBookCommand(request.isbn(), request.title(), authors, request.publisher().name(), "");
    }
}