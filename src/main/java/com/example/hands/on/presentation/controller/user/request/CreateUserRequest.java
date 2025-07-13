package com.example.hands.on.presentation.controller.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
    @NotBlank(message = "名前は必須です")
    String name,
    
    @NotBlank(message = "メールアドレスは必須です")
    @Email(message = "メールアドレスの形式が正しくありません")
    String email
) {}