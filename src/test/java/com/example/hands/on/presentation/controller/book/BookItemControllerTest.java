package com.example.hands.on.presentation.controller.book;

import com.example.hands.on.presentation.controller.book.request.CreateBookItemRequest;
import com.example.hands.on.presentation.controller.book.request.UpdateBookItemRequest;
import com.example.hands.on.presentation.controller.book.response.BookItemResponse;
import com.example.hands.on.usecase.book.BookItemUseCase;
import com.example.hands.on.usecase.book.command.CreateBookItemCommand;
import com.example.hands.on.usecase.book.command.UpdateBookItemCommand;
import com.example.hands.on.usecase.book.dto.BookItemDto;
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

@WebMvcTest(BookItemController.class)
class BookItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookItemUseCase bookItemUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void すべての蔵書を取得できること() throws Exception {
        List<BookItemDto> bookItems = Arrays.asList(
                createBookItemDto(1L, "978-4-123456-78-0", "AVAILABLE"),
                createBookItemDto(2L, "978-4-123456-78-1", "LENT")
        );
        when(bookItemUseCase.getAllBookItems()).thenReturn(bookItems);

        mockMvc.perform(get("/api/book-items"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].status").value("AVAILABLE"));
    }

    @Test
    void IDで特定の蔵書を取得できること() throws Exception {
        BookItemDto bookItemDto = createBookItemDto(1L, "978-4-123456-78-0", "AVAILABLE");
        when(bookItemUseCase.getBookItemById(1L)).thenReturn(bookItemDto);

        mockMvc.perform(get("/api/book-items/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.isbn").value("978-4-123456-78-0"))
                .andExpect(jsonPath("$.status").value("AVAILABLE"));
    }

    @Test
    void ISBNで蔵書一覧を取得できること() throws Exception {
        List<BookItemDto> bookItems = Arrays.asList(
                createBookItemDto(1L, "978-4-123456-78-0", "AVAILABLE"),
                createBookItemDto(2L, "978-4-123456-78-0", "LENT")
        );
        when(bookItemUseCase.getBookItemsByIsbn("978-4-123456-78-0")).thenReturn(bookItems);

        mockMvc.perform(get("/api/book-items/book/978-4-123456-78-0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].isbn").value("978-4-123456-78-0"))
                .andExpect(jsonPath("$[1].isbn").value("978-4-123456-78-0"));
    }

    @Test
    void ISBNで貸出可能な蔵書一覧を取得できること() throws Exception {
        List<BookItemDto> availableItems = Arrays.asList(
                createBookItemDto(1L, "978-4-123456-78-0", "AVAILABLE")
        );
        when(bookItemUseCase.getAvailableBookItemsByIsbn("978-4-123456-78-0")).thenReturn(availableItems);

        mockMvc.perform(get("/api/book-items/available/978-4-123456-78-0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].status").value("AVAILABLE"));
    }

    @Test
    void バリデーションエラーで蔵書作成が失敗すること() throws Exception {
        CreateBookItemRequest invalidRequest = new CreateBookItemRequest("", "INVALID_STATUS");

        mockMvc.perform(post("/api/book-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void バリデーションエラーで蔵書更新が失敗すること() throws Exception {
        UpdateBookItemRequest invalidRequest = new UpdateBookItemRequest("");

        mockMvc.perform(put("/api/book-items/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 存在しないIDでのアクセスが適切にハンドリングされること() throws Exception {
        when(bookItemUseCase.getBookItemById(999L)).thenThrow(new RuntimeException("BookItem not found"));

        mockMvc.perform(get("/api/book-items/999"))
                .andExpect(status().isInternalServerError());
    }

    private BookItemDto createBookItemDto(Long id, String isbn, String status) {
        LocalDateTime now = LocalDateTime.now();
        return new BookItemDto(id, isbn, "", status, now, now);
    }
}