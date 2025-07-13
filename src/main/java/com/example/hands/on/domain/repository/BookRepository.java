package com.example.hands.on.domain.repository;

import com.example.hands.on.domain.model.book.entity.Book;
import com.example.hands.on.domain.model.book.value.Isbn;
import com.example.hands.on.usecase.book.command.SearchBooksCommand;
import com.example.hands.on.usecase.dto.PageDto;

import java.util.Optional;

public interface BookRepository {
    
    /**
     * 書籍を検索する
     * @param command 検索条件
     * @param page ページネーション情報
     * @return 検索結果の書籍ページ
     */
    PageDto<Book> searchBooks(SearchBooksCommand command, int page, int size);
    
    /**
     * ISBNで書籍を取得する
     * @param isbn ISBN
     * @return 書籍（存在しない場合はempty）
     */
    Optional<Book> findByIsbn(Isbn isbn);
    
    /**
     * ISBNで書籍の存在を確認する
     * @param isbn ISBN
     * @return 存在する場合true
     */
    boolean existsByIsbn(Isbn isbn);
    
    /**
     * 書籍を保存する
     * @param book 書籍
     * @return 保存された書籍
     */
    Book save(Book book);
}