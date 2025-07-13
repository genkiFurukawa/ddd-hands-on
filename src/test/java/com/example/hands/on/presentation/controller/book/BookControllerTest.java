package com.example.hands.on.presentation.controller.book;

import com.example.hands.on.presentation.controller.book.request.CreateBookRequest;
import com.example.hands.on.usecase.book.BookUseCase;
import com.example.hands.on.usecase.book.command.CreateBookCommand;
import com.example.hands.on.usecase.book.command.SearchBooksCommand;
import com.example.hands.on.usecase.book.dto.BookAvailabilityDto;
import com.example.hands.on.usecase.book.dto.BookDto;
import com.example.hands.on.usecase.book.dto.BookItemDto;
import com.example.hands.on.usecase.common.dto.PageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookUseCase bookUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void すべての書籍を取得できること() throws Exception {
        List<BookDto> books = Arrays.asList(
                createBookDto("978-4-123456-78-0", "テスト書籍1", "テスト著者1", "テスト出版社1"),
                createBookDto("978-4-123456-78-1", "テスト書籍2", "テスト著者2", "テスト出版社2")
        );
        PageDto<BookDto> pageDto = new PageDto<>(books, 0, 20, 2L, 1, true, true);
        when(bookUseCase.searchBooks(any(SearchBooksCommand.class))).thenReturn(pageDto);

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.totalElements").value(2));
    }

    @Test
    void 検索条件を指定して書籍を取得できること() throws Exception {
        List<BookDto> books = Arrays.asList(
                createBookDto("978-4-123456-78-0", "Java入門", "山田太郎", "技術出版社")
        );
        PageDto<BookDto> pageDto = new PageDto<>(books, 0, 10, 1L, 1, true, true);
        when(bookUseCase.searchBooks(any(SearchBooksCommand.class))).thenReturn(pageDto);

        mockMvc.perform(get("/api/books")
                        .param("title", "Java")
                        .param("author", "山田")
                        .param("publisher", "技術")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].title").value("Java入門"));
    }

    @Test
    void ページングパラメータのバリデーションが機能すること() throws Exception {
        mockMvc.perform(get("/api/books")
                        .param("page", "-1")
                        .param("size", "101"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void ISBNで特定の書籍を取得できること() throws Exception {
        BookDto bookDto = createBookDto("978-4-123456-78-0", "テスト書籍", "テスト著者", "テスト出版社");
        when(bookUseCase.getBookByIsbn("978-4-123456-78-0")).thenReturn(bookDto);

        mockMvc.perform(get("/api/books/978-4-123456-78-0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value("978-4-123456-78-0"))
                .andExpect(jsonPath("$.title").value("テスト書籍"));
    }

    @Test
    void 書籍を作成できること() throws Exception {
        CreateBookRequest request = createBookRequest();
        BookDto bookDto = createBookDto("978-4-123456-78-0", "新しい書籍", "新しい著者", "新しい出版社");
        when(bookUseCase.createBook(any(CreateBookCommand.class))).thenReturn(bookDto);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.isbn").value("978-4-123456-78-0"))
                .andExpect(jsonPath("$.title").value("新しい書籍"));
    }

    @Test
    void バリデーションエラーで書籍作成が失敗すること() throws Exception {
        CreateBookRequest invalidRequest = new CreateBookRequest(
                "", "", List.of(), new CreateBookRequest.PublisherRequest("")
        );

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 書籍の蔵書一覧を取得できること() throws Exception {
        List<BookItemDto> bookItems = Arrays.asList(
                createBookItemDto(1L, "978-4-123456-78-0", "AVAILABLE"),
                createBookItemDto(2L, "978-4-123456-78-0", "LENT")
        );
        when(bookUseCase.getBookItems("978-4-123456-78-0", null)).thenReturn(bookItems);

        mockMvc.perform(get("/api/books/978-4-123456-78-0/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].status").value("AVAILABLE"))
                .andExpect(jsonPath("$[1].status").value("LENT"));
    }

    @Test
    void ステータスを指定して蔵書一覧を取得できること() throws Exception {
        List<BookItemDto> availableItems = Arrays.asList(
                createBookItemDto(1L, "978-4-123456-78-0", "AVAILABLE")
        );
        when(bookUseCase.getBookItems("978-4-123456-78-0", "AVAILABLE")).thenReturn(availableItems);

        mockMvc.perform(get("/api/books/978-4-123456-78-0/items")
                        .param("status", "AVAILABLE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].status").value("AVAILABLE"));
    }

    @Test
    void 書籍の在庫状況を取得できること() throws Exception {
        BookAvailabilityDto availabilityDto = new BookAvailabilityDto(
                "978-4-123456-78-0", 5, 2, 2, 1
        );
        when(bookUseCase.getBookAvailability("978-4-123456-78-0")).thenReturn(availabilityDto);

        mockMvc.perform(get("/api/books/978-4-123456-78-0/availability"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value("978-4-123456-78-0"))
                .andExpect(jsonPath("$.totalCopies").value(5))
                .andExpect(jsonPath("$.availableCopies").value(2))
                .andExpect(jsonPath("$.lentCopies").value(2))
                .andExpect(jsonPath("$.reservedCopies").value(1));
    }

    private BookDto createBookDto(String isbn, String title, String authorName, String publisherName) {
        List<String> authors = List.of(authorName);
        LocalDateTime now = LocalDateTime.now();
        return new BookDto(isbn, title, authors, publisherName, "", now, now);
    }

    private BookItemDto createBookItemDto(Long id, String isbn, String status) {
        LocalDateTime now = LocalDateTime.now();
        return new BookItemDto(id, isbn, "", status, now, now);
    }

    private CreateBookRequest createBookRequest() {
        return new CreateBookRequest(
                "978-4-123456-78-0",
                "新しい書籍",
                List.of(new CreateBookRequest.AuthorRequest("新しい著者")),
                new CreateBookRequest.PublisherRequest("新しい出版社")
        );
    }
}