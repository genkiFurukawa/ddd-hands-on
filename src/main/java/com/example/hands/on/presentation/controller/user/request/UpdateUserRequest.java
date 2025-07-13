package com.example.hands.on.presentation.controller.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateUserRequest(
    @NotBlank(message = "名前は必須です")
    String name,
    
    @Email(message = "メールアドレスの形式が正しくありません")
    String email,
    
    @Pattern(regexp = "ACTIVE|SUSPENDED|EXPIRED", message = "不正なステータス値です")
    String status
) {}