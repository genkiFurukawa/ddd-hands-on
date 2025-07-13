# DDD hands-on
## 概要

図書館管理システムのハンズオンプロジェクトです。

## API概要

### 書籍管理 (Books)

| メソッド | エンドポイント | 説明 |
|---------|---------------|------|
| GET | `/api/books` | 書籍一覧を取得（検索・ページング対応） |
| GET | `/api/books/{isbn}` | ISBN指定で書籍詳細を取得 |
| POST | `/api/books` | 新しい書籍を作成 |
| GET | `/api/books/{isbn}/items` | 指定書籍の蔵書一覧を取得 |
| GET | `/api/books/{isbn}/availability` | 指定書籍の在庫状況を取得 |

### 蔵書管理 (Book Items)

| メソッド | エンドポイント | 説明 |
|---------|---------------|------|
| GET | `/api/book-items` | 全蔵書一覧を取得 |
| GET | `/api/book-items/{id}` | ID指定で蔵書詳細を取得 |
| GET | `/api/book-items/book/{isbn}` | ISBN指定で蔵書一覧を取得 |
| GET | `/api/book-items/available/{isbn}` | ISBN指定で貸出可能な蔵書一覧を取得 |
| POST | `/api/book-items` | 新しい蔵書を作成 |
| PUT | `/api/book-items/{id}` | 蔵書情報を更新 |
| DELETE | `/api/book-items/{id}` | 蔵書を削除 |

### 貸出管理 (Lending)

| メソッド | エンドポイント | 説明 |
|---------|---------------|------|
| POST | `/api/lendings` | 書籍の貸出を実行 |
| PATCH | `/api/lendings/{lendingId}/return` | 書籍の返却を実行 |
| GET | `/api/lendings/overdue` | 延滞中の貸出一覧を取得 |
| GET | `/api/lendings/active` | アクティブな貸出一覧を取得 |

### 予約管理 (Reservations)

| メソッド | エンドポイント | 説明 |
|---------|---------------|------|
| POST | `/api/reservations` | 書籍の予約を作成 |
| PATCH | `/api/reservations/{reservationId}/cancel` | 予約をキャンセル |
| PATCH | `/api/reservations/{reservationId}/fulfill` | 予約を実現（貸出に変換） |

### ユーザー管理 (Users)

| メソッド | エンドポイント | 説明 |
|---------|---------------|------|
| GET | `/api/users` | ユーザー一覧を取得（検索・ページング対応） |
| GET | `/api/users/{id}` | ID指定でユーザー詳細を取得 |
| POST | `/api/users` | 新しいユーザーを作成 |
| PUT | `/api/users/{id}` | ユーザー情報を更新 |
| GET | `/api/users/{userId}/lendings` | ユーザーの貸出履歴を取得 |
| GET | `/api/users/{userId}/reservations` | ユーザーの予約履歴を取得 |