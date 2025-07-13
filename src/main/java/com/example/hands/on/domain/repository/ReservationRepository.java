package com.example.hands.on.domain.repository;

import com.example.hands.on.domain.model.lending.entity.Reservation;
import com.example.hands.on.domain.model.lending.value.ReservationStatus;
import com.example.hands.on.domain.model.book.value.Isbn;
import com.example.hands.on.usecase.dto.PageDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    
    /**
     * 予約記録を保存する
     * @param reservation 予約記録
     * @return 保存された予約記録
     */
    Reservation save(Reservation reservation);
    
    /**
     * IDで予約記録を取得する
     * @param id 予約ID
     * @return 予約記録（存在しない場合はempty）
     */
    Optional<Reservation> findById(Long id);
    
    /**
     * ユーザーIDで予約記録を取得する
     * @param userId ユーザーID
     * @return 該当ユーザーの予約記録リスト
     */
    List<Reservation> findByUserId(Long userId);
    
    /**
     * ユーザーIDとステータスで予約記録を取得する
     * @param userId ユーザーID
     * @param status 予約ステータス
     * @return 該当する予約記録リスト
     */
    List<Reservation> findByUserIdAndStatus(Long userId, ReservationStatus status);
    
    /**
     * ユーザーIDで予約記録をページネーションで取得する
     * @param userId ユーザーID
     * @param status ステータス（nullの場合は全ステータス）
     * @param page ページネーション情報
     * @return 予約記録のページ
     */
    PageDto<Reservation> findByUserIdWithPagination(Long userId, ReservationStatus status, int page, int size);
    
    /**
     * ユーザーIDとISBNでアクティブな予約を取得する
     * @param userId ユーザーID
     * @param isbn ISBN
     * @return アクティブな予約記録（存在しない場合はempty）
     */
    Optional<Reservation> findActiveReservationByUserIdAndIsbn(Long userId, Isbn isbn);
    
    /**
     * ISBNでアクティブな予約記録を取得する（待ち順序順）
     * @param isbn ISBN
     * @return 待ち順序順のアクティブな予約記録リスト
     */
    List<Reservation> findActiveReservationsByIsbnOrderByReservedDate(Isbn isbn);
    
    /**
     * ISBNでアクティブな予約数を取得する
     * @param isbn ISBN
     * @return アクティブな予約数
     */
    long countActiveReservationsByIsbn(Isbn isbn);
    
    /**
     * ユーザーのアクティブな予約数を取得する
     * @param userId ユーザーID
     * @return アクティブな予約数
     */
    long countActiveReservationsByUserId(Long userId);
    
    /**
     * 期限切れの予約記録を取得する
     * @param currentDate 現在日付
     * @param page ページネーション情報
     * @return 期限切れの予約記録ページ
     */
    PageDto<Reservation> findExpiredReservations(LocalDate currentDate, int page, int size);
    
    /**
     * 取り置き準備完了の予約記録を取得する
     * @param page ページネーション情報
     * @return 取り置き準備完了の予約記録ページ
     */
    PageDto<Reservation> findReadyReservations(int page, int size);
    
    /**
     * ISBNの次の待ち予約を取得する
     * @param isbn ISBN
     * @return 次の待ち予約（存在しない場合はempty）
     */
    Optional<Reservation> findNextWaitingReservationByIsbn(Isbn isbn);
}