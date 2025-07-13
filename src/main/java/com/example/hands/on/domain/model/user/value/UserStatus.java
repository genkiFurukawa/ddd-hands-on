package com.example.hands.on.domain.model.user.value;

public enum UserStatus {
    ACTIVE(1, "有効"),
    SUSPENDED(2, "利用停止"),
    EXPIRED(3, "期限切れ");

    private final int status;
    private final String displayName;

    UserStatus(int status, String displayName) {
        this.status = status;
        this.displayName = displayName;
    }

    public boolean canBorrow() {
        return this == ACTIVE;
    }

    public int getStatus() {
        return status;
    }

    public String getDisplayName() {
        return displayName;
    }
}