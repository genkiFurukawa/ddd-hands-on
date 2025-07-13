package com.example.hands.on.domain.model.book.value;

public enum BookItemStatus {
    AVAILABLE(1, "利用可能"), // "貸し出し可能な状態"
    LENT(2, "貸し出し中"), //, "会員に貸し出し中"
    RESERVED(3, "予約済み"), // "予約が入っているため貸し出し準備中"
    UNDER_REPAIR(4, "修理中"), // "破損等により修理中"
    LOST(5, "紛失"), // "紛失により利用不可"
    DAMAGED(6, "破損"), // "破損により利用不可"
    TO_BE_DISCARDED(7, "廃棄予定"), // "廃棄処理予定"
    NOT_AVAILABLE(8, "貸し出し不可"); // "その他の理由により貸し出し不可"

    private final int status;
    private final String displayName;

    BookItemStatus(int status, String displayName) {
        this.status = status;
        this.displayName = displayName;
    }

    public boolean canLend() {
        return this == AVAILABLE;
    }

    public boolean isActive() {
        return this != TO_BE_DISCARDED && this != LOST;
    }
}