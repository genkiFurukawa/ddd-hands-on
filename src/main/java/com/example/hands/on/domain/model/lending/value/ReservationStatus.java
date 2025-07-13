package com.example.hands.on.domain.model.lending.value;

public enum ReservationStatus {
    WAITING(1, "予約待ち"),
    READY(2, "取り置き準備完了"),
    FULFILLED(3, "貸し出し完了"),
    CANCELLED(4, "キャンセル"),
    EXPIRED(5, "期限切れ");

    private final int status;
    private final String displayName;

    ReservationStatus(int status, String displayName) {
        this.status = status;
        this.displayName = displayName;
    }

    public boolean canCancel() {
        return this == WAITING || this == READY;
    }

    public boolean isActive() {
        return this == WAITING || this == READY;
    }

    public int getStatus() {
        return status;
    }

    public String getDisplayName() {
        return displayName;
    }
}