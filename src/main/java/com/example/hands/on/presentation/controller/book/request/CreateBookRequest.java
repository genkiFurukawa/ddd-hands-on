package com.example.hands.on.presentation.controller.book.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.List;

public record CreateBookRequest(
    @NotBlank(message = "ISBNは必須です")
    @Pattern(regexp = "^(97[89])?[0-9]{9}[0-9X]$", message = "ISBN形式が不正です")
    String isbn,
    
    @NotBlank(message = "タイトルは必須です")
    String title,
    
    @NotEmpty(message = "著者リストは必須です")
    @Valid
    List<AuthorRequest> authors,
    
    @NotNull(message = "出版社情報は必須です")
    @Valid
    PublisherRequest publisher
) {
    public record AuthorRequest(
        @NotBlank(message = "著者名は必須です")
        String name
    ) {}
    
    public record PublisherRequest(
        @NotBlank(message = "出版社名は必須です")
        String name
    ) {}
}