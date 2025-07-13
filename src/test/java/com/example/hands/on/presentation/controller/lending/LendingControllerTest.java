package com.example.hands.on.presentation.controller.lending;

import com.example.hands.on.presentation.controller.lending.request.LendBookRequest;
import com.example.hands.on.presentation.controller.lending.response.LendingResponse;
import com.example.hands.on.usecase.lending.LendingUseCase;
import com.example.hands.on.usecase.lending.command.LendBookCommand;
import com.example.hands.on.usecase.lending.dto.LendingDto;
import com.example.hands.on.usecase.common.dto.PageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LendingController.class)
class LendingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LendingUseCase lendingUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 書籍を貸出できること() throws Exception {
        LendBookRequest request = new LendBookRequest(1L, 1L, LocalDate.now().plusDays(14));
        LendingDto lendingDto = createLendingDto(1L, 1L, 1L, "ACTIVE");
        when(lendingUseCase.lendBook(any(LendBookCommand.class))).thenReturn(lendingDto);

        mockMvc.perform(post("/api/lendings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.bookItemId").value(1))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void バリデーションエラーで貸出が失敗すること() throws Exception {
        LendBookRequest invalidRequest = new LendBookRequest(null, null, null);

        mockMvc.perform(post("/api/lendings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 書籍を返却できること() throws Exception {
        LendingDto returnedDto = createReturnedLendingDto(1L, 1L, 1L);
        when(lendingUseCase.returnBook(1L)).thenReturn(returnedDto);

        mockMvc.perform(patch("/api/lendings/1/return"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("RETURNED"));
    }

    @Test
    void 延滞中の貸出一覧を取得できること() throws Exception {
        List<LendingDto> overdueLendings = Arrays.asList(
                createOverdueLendingDto(1L, 1L, 1L),
                createOverdueLendingDto(2L, 2L, 2L)
        );
        PageDto<LendingDto> pageDto = new PageDto<>(overdueLendings, 0, 20, 2L, 1, true, true);
        when(lendingUseCase.getOverdueLendings(anyInt(), anyInt())).thenReturn(pageDto);

        mockMvc.perform(get("/api/lendings/overdue"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.totalElements").value(2));
    }

    @Test
    void アクティブな貸出一覧を取得できること() throws Exception {
        List<LendingDto> activeLendings = Arrays.asList(
                createLendingDto(1L, 1L, 1L, "ACTIVE"),
                createLendingDto(2L, 2L, 2L, "ACTIVE")
        );
        PageDto<LendingDto> pageDto = new PageDto<>(activeLendings, 0, 20, 2L, 1, true, true);
        when(lendingUseCase.getActiveLendings(anyInt(), anyInt())).thenReturn(pageDto);

        mockMvc.perform(get("/api/lendings/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].status").value("ACTIVE"));
    }

    @Test
    void ページングパラメータを指定して延滞一覧を取得できること() throws Exception {
        List<LendingDto> overdueLendings = Arrays.asList(
                createOverdueLendingDto(1L, 1L, 1L)
        );
        PageDto<LendingDto> pageDto = new PageDto<>(overdueLendings, 0, 10, 1L, 1, true, true);
        when(lendingUseCase.getOverdueLendings(0, 10)).thenReturn(pageDto);

        mockMvc.perform(get("/api/lendings/overdue")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.size").value(10))
                .andExpect(jsonPath("$.page").value(0));
    }

    @Test
    void ページングパラメータのバリデーションが機能すること() throws Exception {
        mockMvc.perform(get("/api/lendings/overdue")
                        .param("page", "-1")
                        .param("size", "101"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 存在しない貸出の返却が適切にハンドリングされること() throws Exception {
        when(lendingUseCase.returnBook(999L)).thenThrow(new RuntimeException("Lending not found"));

        mockMvc.perform(patch("/api/lendings/999/return"))
                .andExpect(status().isInternalServerError());
    }

    private LendingDto createLendingDto(Long id, Long userId, Long bookItemId, String status) {
        LocalDateTime now = LocalDateTime.now();
        return new LendingDto(id, userId, bookItemId, LocalDate.now(), LocalDate.now().plusDays(14), null, status, now, now);
    }

    private LendingDto createReturnedLendingDto(Long id, Long userId, Long bookItemId) {
        LocalDateTime now = LocalDateTime.now();
        return new LendingDto(id, userId, bookItemId, LocalDate.now().minusDays(7), 
                LocalDate.now().plusDays(7), LocalDate.now(), "RETURNED", now, now);
    }

    private LendingDto createOverdueLendingDto(Long id, Long userId, Long bookItemId) {
        LocalDateTime now = LocalDateTime.now();
        return new LendingDto(id, userId, bookItemId, LocalDate.now().minusDays(21), 
                LocalDate.now().minusDays(7), null, "ACTIVE", now, now);
    }
}