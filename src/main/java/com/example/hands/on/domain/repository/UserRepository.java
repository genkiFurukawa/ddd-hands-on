package com.example.hands.on.domain.repository;

import com.example.hands.on.domain.model.user.entity.User;
import com.example.hands.on.domain.model.user.value.UserStatus;
import com.example.hands.on.usecase.user.command.SearchUsersCommand;
import com.example.hands.on.usecase.dto.PageDto;

import java.util.Optional;

public interface UserRepository {
    
    /**
     * ユーザーを検索する
     * @param command 検索条件
     * @param page ページネーション情報
     * @return 検索結果のユーザーページ
     */
    PageDto<User> searchUsers(SearchUsersCommand command, int page, int size);
    
    /**
     * IDでユーザーを取得する
     * @param id ユーザーID
     * @return ユーザー（存在しない場合はempty）
     */
    Optional<User> findById(Long id);
    
    /**
     * メールアドレスでユーザーを取得する
     * @param email メールアドレス
     * @return ユーザー（存在しない場合はempty）
     */
    Optional<User> findByEmail(String email);
    
    /**
     * メールアドレスでユーザーの存在を確認する
     * @param email メールアドレス
     * @return 存在する場合true
     */
    boolean existsByEmail(String email);
    
    /**
     * ステータスでユーザーを取得する
     * @param status ユーザーステータス
     * @param page ページネーション情報
     * @return 該当ステータスのユーザーページ
     */
    PageDto<User> findByStatus(UserStatus status, int page, int size);
    
    /**
     * ユーザーを保存する
     * @param user ユーザー
     * @return 保存されたユーザー
     */
    User save(User user);
    
    /**
     * 全てのユーザーを取得する
     * @param page ページネーション情報
     * @return ユーザーページ
     */
    PageDto<User> findAll(int page, int size);
}