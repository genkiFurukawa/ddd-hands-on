package com.example.hands.on.domain.model.book.value;

public record Isbn(String value) {
    public Isbn(String value) {
        if (!isValidISBN(value)) {
            throw new IllegalArgumentException("無効なISBN形式です: " + value);
        }
        this.value = normalizeISBN(value);
    }

    private boolean isValidISBN(String isbn) {
        // ISBN-10またはISBN-13の形式チェック
        return isbn.matches("^(97[89])?[0-9]{9}[0-9X]$");
    }

    private String normalizeISBN(String isbn) {
        // ハイフンを除去して正規化
        return isbn.replaceAll("-", "");
    }
}