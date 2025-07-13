package com.example.hands.on.domain.model.lending.value;

public enum LendingStatus {
    ACTIVE(1, "貸し出し中"),
    OVERDUE(2, "延滞中"),
    RETURNED(3, "返却済み"),
    LOST(4, "紛失"),
    DAMAGED_RETURN(5, "破損返却");

    private final int status;
    private final String displayName;

    LendingStatus(int status, String displayName) {
        this.status = status;
        this.displayName = displayName;
    }

    public boolean isActive() {
        return this == ACTIVE || this == OVERDUE;
    }

    public boolean requiresFee() {
        return this == OVERDUE || this == LOST || this == DAMAGED_RETURN;
    }

    public int getStatus() {
        return status;
    }

    public String getDisplayName() {
        return displayName;
    }
}