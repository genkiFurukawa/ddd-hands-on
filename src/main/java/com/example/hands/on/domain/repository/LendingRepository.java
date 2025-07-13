package com.example.hands.on.domain.repository;

import com.example.hands.on.domain.model.lending.entity.Lending;
import com.example.hands.on.domain.model.lending.value.LendingStatus;
import com.example.hands.on.domain.model.book.value.Isbn;
import com.example.hands.on.usecase.dto.PageDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LendingRepository {
    
    /**
     * 貸出記録を保存する
     * @param lending 貸出記録
     * @return 保存された貸出記録
     */
    Lending save(Lending lending);
    
    /**
     * IDで貸出記録を取得する
     * @param id 貸出ID
     * @return 貸出記録（存在しない場合はempty）
     */
    Optional<Lending> findById(Long id);
    
    /**
     * ユーザーIDで貸出記録を取得する
     * @param userId ユーザーID
     * @return 該当ユーザーの貸出記録リスト
     */
    List<Lending> findByUserId(Long userId);
    
    /**
     * ユーザーIDとステータスで貸出記録を取得する
     * @param userId ユーザーID
     * @param status 貸出ステータス
     * @return 該当する貸出記録リスト
     */
    List<Lending> findByUserIdAndStatus(Long userId, LendingStatus status);
    
    /**
     * ユーザーIDで貸出記録をページネーションで取得する
     * @param userId ユーザーID
     * @param status ステータス（nullの場合は全ステータス）
     * @param page ページネーション情報
     * @return 貸出記録のページ
     */
    PageDto<Lending> findByUserIdWithPagination(Long userId, LendingStatus status, int page, int size);
    
    /**
     * 延滞中の貸出記録を取得する
     * @param currentDate 現在日付
     * @param page ページネーション情報
     * @return 延滞中の貸出記録ページ
     */
    PageDto<Lending> findOverdueLendings(LocalDate currentDate, int page, int size);
    
    /**
     * アクティブな貸出記録を取得する
     * @param pageable ページネーション情報
     * @return アクティブな貸出記録ページ
     */
    PageDto<Lending> findActiveLendings(int page, int size);
    
    /**
     * ISBNでアクティブな貸出数を取得する
     * @param isbn ISBN
     * @return アクティブな貸出数
     */
    long countActiveLendingsByIsbn(Isbn isbn);
    
    /**
     * ユーザーのアクティブな貸出数を取得する
     * @param userId ユーザーID
     * @return アクティブな貸出数
     */
    long countActiveLendingsByUserId(Long userId);
    
    /**
     * ユーザーの延滞中の貸出数を取得する
     * @param userId ユーザーID
     * @param currentDate 現在日付
     * @return 延滞中の貸出数
     */
    long countOverdueLendingsByUserId(Long userId, LocalDate currentDate);
    
    /**
     * 書籍アイテムIDでアクティブな貸出記録を取得する
     * @param bookItemId 書籍アイテムID
     * @return アクティブな貸出記録（存在しない場合はempty）
     */
    Optional<Lending> findActiveLendingByBookItemId(Long bookItemId);
}