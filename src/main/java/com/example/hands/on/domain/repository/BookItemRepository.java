package com.example.hands.on.domain.repository;

import com.example.hands.on.domain.model.book.entity.BookItem;
import com.example.hands.on.domain.model.book.value.BookItemStatus;
import com.example.hands.on.domain.model.book.value.Isbn;
import com.example.hands.on.usecase.dto.PageDto;

import java.util.List;
import java.util.Optional;

public interface BookItemRepository {
    
    /**
     * 全ての書籍アイテムを取得する
     * @param page ページネーション情報
     * @return 書籍アイテムのページ
     */
    PageDto<BookItem> findAll(int page, int size);
    
    /**
     * IDで書籍アイテムを取得する
     * @param id 書籍アイテムID
     * @return 書籍アイテム（存在しない場合はempty）
     */
    Optional<BookItem> findById(Long id);
    
    /**
     * ISBNで書籍アイテムを取得する
     * @param isbn ISBN
     * @return 該当ISBNの書籍アイテムリスト
     */
    List<BookItem> findByIsbn(Isbn isbn);
    
    /**
     * ISBNとステータスで書籍アイテムを取得する
     * @param isbn ISBN
     * @param status ステータス
     * @return 該当する書籍アイテムリスト
     */
    List<BookItem> findByIsbnAndStatus(Isbn isbn, BookItemStatus status);
    
    /**
     * ISBNで利用可能な書籍アイテムを取得する
     * @param isbn ISBN
     * @return 利用可能な書籍アイテムリスト
     */
    List<BookItem> findAvailableByIsbn(Isbn isbn);
    
    /**
     * ISBNでステータス別の書籍アイテム数を取得する
     * @param isbn ISBN
     * @param status ステータス
     * @return 該当する書籍アイテム数
     */
    long countByIsbnAndStatus(Isbn isbn, BookItemStatus status);
    
    /**
     * ISBNで書籍アイテムの総数を取得する
     * @param isbn ISBN
     * @return 書籍アイテム総数
     */
    long countByIsbn(Isbn isbn);
    
    /**
     * 書籍アイテムを保存する
     * @param bookItem 書籍アイテム
     * @return 保存された書籍アイテム
     */
    BookItem save(BookItem bookItem);
    
    /**
     * IDで書籍アイテムを削除する
     * @param id 書籍アイテムID
     */
    void deleteById(Long id);
    
    /**
     * 書籍アイテムが削除可能かチェックする（貸出中・予約中でない）
     * @param id 書籍アイテムID
     * @return 削除可能な場合true
     */
    boolean canDelete(Long id);
}