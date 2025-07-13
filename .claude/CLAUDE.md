# DDD ハンズオン

## Claude Code へ

- あなたは Eric Evans のようにドメインを設計し、 t-wada のように実装することを信条とするエンジニアです。

## 1. システム概要

### 1.1 目的

図書館の本の貸し出し管理を行うシステムのハンズオン用サンプルアプリケーション

### 1.2 アーキテクチャ

- **アーキテクチャパターン**: オニオンアーキテクチャ
- **API形式**: REST API
- **フレームワーク**: Spring Boot
- **永続化層**: H2 Database
- **テスティングフレームワーク**: JUnit5

### 1.3 前提条件

- ハンズオン用の簡略化されたシステム
- 認証・認可は考慮しない
- 同時実行制御は考慮しない
- マスタデータの更新や削除はAPIに不要
- 一覧取得APIはページングは考慮不要

## 2. ドメインモデル ER図

```mermaid
erDiagram
    User {
        Long id PK
        String name
        String email
        LocalDate registeredDate
        UserStatus status
    }
    
    Book {
        Isbn isbn PK
        String title
        List_Author authors
        Publisher publisher
    }
    
    Author {
        Long id PK
        String name
    }
    
    Publisher {
        Long id PK
        String name
    }
    
    BookItem {
        long id PK
        Isbn isbn FK
        BookItemStatus status
    }
    
    Lending {
        Long id PK
        Long userId FK
        long bookItemId FK
        LocalDate lentDate
        LocalDate dueDate
        LocalDate returnedDate
        LendingStatus status
    }
    
    Reservation {
        Long id PK
        Long userId FK
        Isbn bookIsbn FK
        LocalDate reservedDate
        LocalDate expiryDate
        ReservationStatus status
    }

    %% Relationships
    User ||--o{ Lending : "borrows"
    User ||--o{ Reservation : "reserves"
    Book ||--o{ BookItem : "has_copies"
    Book ||--o{ Reservation : "reserved_for"
    BookItem ||--o{ Lending : "lent_as"
    Book }o--|| Publisher : "published_by"
    Book }o--o{ Author : "written_by"
    
    %% Value Objects and Enums
    UserStatus {
        ACTIVE
        SUSPENDED
        EXPIRED
    }
    
    BookItemStatus {
        AVAILABLE
        LENT
        RESERVED
        UNDER_REPAIR
        LOST
        DAMAGED
        TO_BE_DISCARDED
        NOT_AVAILABLE
    }
    
    LendingStatus {
        ACTIVE
        OVERDUE
        RETURNED
        LOST
        DAMAGED_RETURN
    }
    
    ReservationStatus {
        WAITING
        READY
        FULFILLED
        CANCELLED
        EXPIRED
    }
```

### 2.1 エンティティ説明

- **User**: 図書館利用者
- **Book**: 書籍の論理的な概念（ISBN、タイトル等）
- **BookItem**: 蔵書
- **Author**: 著者
- **Publisher**: 出版社
- **Lending**: 貸し出し記録
- **Reservation**: 予約記録

### 2.2 主要な関連

- 1つの Book は複数の BookItem を持つ（同じ本の複数コピー）
- User は複数の Lending と Reservation を持てる
- BookItem は複数の Lending 履歴を持つ
- Book は複数の Reservation を受けられる

## 3. API仕様

### 3.1 ユーザー管理API

#### GET /api/users
ユーザー一覧取得

**クエリパラメータ:**
- `name` (optional): 名前での部分一致検索
- `email` (optional): メールアドレスでの部分一致検索
- `status` (optional): ユーザーステータス (ACTIVE, SUSPENDED, EXPIRED)
- `page` (optional, default: 0): ページ番号
- `size` (optional, default: 20, max: 100): 1ページあたりの件数

**レスポンス:** 200 OK
```json
{
  "content": [
    {
      "id": 1,
      "name": "山田太郎",
      "email": "yamada@example.com",
      "registeredDate": "2024-01-01",
      "status": "ACTIVE"
    }
  ],
  "totalElements": 1,
  "totalPages": 1,
  "size": 20,
  "number": 0
}
```

**エラーレスポンス:**
- 400 Bad Request: 不正なクエリパラメータ

#### GET /api/users/{userId}
ユーザー詳細取得

**パスパラメータ:**
- `userId`: ユーザーID

**レスポンス:** 200 OK
```json
{
  "id": 1,
  "name": "山田太郎",
  "email": "yamada@example.com",
  "registeredDate": "2024-01-01",
  "status": "ACTIVE"
}
```

**エラーレスポンス:**
- 404 Not Found: 指定されたユーザーが存在しない

#### GET /api/users/{userId}/lendings
ユーザーの貸出履歴取得

**パスパラメータ:**
- `userId`: ユーザーID

**クエリパラメータ:**
- `status` (optional): 貸出ステータス (ACTIVE, OVERDUE, RETURNED, LOST, DAMAGED_RETURN)
- `page` (optional, default: 0): ページ番号
- `size` (optional, default: 20, max: 100): 1ページあたりの件数

**レスポンス:** 200 OK
```json
{
  "content": [
    {
      "id": 1,
      "userId": 1,
      "bookItemId": 1,
      "book": {
        "isbn": "9784873117584",
        "title": "リーダブルコード"
      },
      "lentDate": "2024-01-01",
      "dueDate": "2024-01-15",
      "returnedDate": null,
      "status": "ACTIVE"
    }
  ],
  "totalElements": 1,
  "totalPages": 1,
  "size": 20,
  "number": 0
}
```

**エラーレスポンス:**
- 404 Not Found: 指定されたユーザーが存在しない

#### GET /api/users/{userId}/reservations
ユーザーの予約一覧取得

**パスパラメータ:**
- `userId`: ユーザーID

**クエリパラメータ:**
- `status` (optional): 予約ステータス (WAITING, READY, FULFILLED, CANCELLED, EXPIRED)
- `page` (optional, default: 0): ページ番号
- `size` (optional, default: 20, max: 100): 1ページあたりの件数

**レスポンス:** 200 OK
```json
{
  "content": [
    {
      "id": 1,
      "userId": 1,
      "bookIsbn": "9784873117584",
      "book": {
        "isbn": "9784873117584",
        "title": "リーダブルコード"
      },
      "reservedDate": "2024-01-01",
      "expiryDate": "2024-01-08",
      "status": "WAITING"
    }
  ],
  "totalElements": 1,
  "totalPages": 1,
  "size": 20,
  "number": 0
}
```

**エラーレスポンス:**
- 404 Not Found: 指定されたユーザーが存在しない

### 3.2 書籍管理API

#### GET /api/books
書籍検索・一覧取得

**クエリパラメータ:**
- `title` (optional): タイトルでの部分一致検索
- `author` (optional): 著者名での部分一致検索
- `publisher` (optional): 出版社名での部分一致検索
- `isbn` (optional): ISBN完全一致検索
- `page` (optional, default: 0): ページ番号
- `size` (optional, default: 20, max: 100): 1ページあたりの件数

**レスポンス:** 200 OK
```json
{
  "content": [
    {
      "isbn": "9784873117584",
      "title": "リーダブルコード",
      "authors": [
        {
          "id": 1,
          "name": "ダスティン・ボズウェル"
        }
      ],
      "publisher": {
        "id": 1,
        "name": "オライリージャパン"
      }
    }
  ],
  "totalElements": 1,
  "totalPages": 1,
  "size": 20,
  "number": 0
}
```

**エラーレスポンス:**
- 400 Bad Request: 不正なクエリパラメータ

#### GET /api/books/{isbn}
書籍詳細取得

**パスパラメータ:**
- `isbn`: ISBN

**レスポンス:** 200 OK
```json
{
  "isbn": "9784873117584",
  "title": "リーダブルコード",
  "authors": [
    {
      "id": 1,
      "name": "ダスティン・ボズウェル"
    }
  ],
  "publisher": {
    "id": 1,
    "name": "オライリージャパン"
  }
}
```

**エラーレスポンス:**
- 400 Bad Request: ISBN形式が不正
- 404 Not Found: 指定されたISBNの書籍が存在しない

#### GET /api/books/{isbn}/items
蔵書一覧取得

**パスパラメータ:**
- `isbn`: ISBN

**クエリパラメータ:**
- `status` (optional): 蔵書ステータス (AVAILABLE, LENT, RESERVED, UNDER_REPAIR, LOST, DAMAGED, TO_BE_DISCARDED, NOT_AVAILABLE)

**レスポンス:** 200 OK
```json
[
  {
    "id": 1,
    "isbn": "9784873117584",
    "status": "AVAILABLE"
  },
  {
    "id": 2,
    "isbn": "9784873117584",
    "status": "LENT"
  }
]
```

**エラーレスポンス:**
- 400 Bad Request: ISBN形式が不正
- 404 Not Found: 指定されたISBNの書籍が存在しない

### 3.3 貸出管理API

#### POST /api/lendings
貸出実行

**リクエストボディ:**
```json
{
  "userId": 1,
  "bookItemId": 1,
  "dueDate": "2024-01-15"
}
```

**レスポンス:** 201 Created
```json
{
  "id": 1,
  "userId": 1,
  "bookItemId": 1,
  "book": {
    "isbn": "9784873117584",
    "title": "リーダブルコード"
  },
  "lentDate": "2024-01-01",
  "dueDate": "2024-01-15",
  "returnedDate": null,
  "status": "ACTIVE"
}
```

**エラーレスポンス:**
- 400 Bad Request:
  - ユーザーIDが空またはnull
  - 蔵書IDが空またはnull
  - 返却予定日が空またはnull
  - 返却予定日が過去の日付
  - 返却予定日が貸出日より前
- 404 Not Found: 
  - 指定されたユーザーが存在しない
  - 指定された蔵書が存在しない
- 409 Conflict:
  - ユーザーが貸出停止状態
  - 蔵書が貸出不可状態
  - ユーザーの貸出上限を超過

#### PATCH /api/lendings/{lendingId}/return
返却処理

**パスパラメータ:**
- `lendingId`: 貸出ID

**レスポンス:** 200 OK
```json
{
  "id": 1,
  "userId": 1,
  "bookItemId": 1,
  "book": {
    "isbn": "9784873117584",
    "title": "リーダブルコード"
  },
  "lentDate": "2024-01-01",
  "dueDate": "2024-01-15",
  "returnedDate": "2024-01-10",
  "status": "RETURNED"
}
```

**エラーレスポンス:**
- 404 Not Found: 指定された貸出記録が存在しない
- 409 Conflict: 既に返却済みの貸出記録

#### GET /api/lendings/overdue
延滞貸出一覧取得

**クエリパラメータ:**
- `page` (optional, default: 0): ページ番号
- `size` (optional, default: 20): 1ページあたりの件数

**レスポンス:** 200 OK
```json
{
  "content": [
    {
      "id": 1,
      "userId": 1,
      "userName": "山田太郎",
      "bookItemId": 1,
      "book": {
        "isbn": "9784873117584",
        "title": "リーダブルコード"
      },
      "lentDate": "2024-01-01",
      "dueDate": "2024-01-15",
      "overdueDays": 5,
      "status": "OVERDUE"
    }
  ],
  "totalElements": 1,
  "totalPages": 1,
  "size": 20,
  "number": 0
}
```

### 3.4 予約管理API

#### POST /api/reservations
予約作成

**リクエストボディ:**
```json
{
  "userId": 1,
  "bookIsbn": "9784873117584"
}
```

**レスポンス:** 201 Created
```json
{
  "id": 1,
  "userId": 1,
  "bookIsbn": "9784873117584",
  "book": {
    "isbn": "9784873117584",
    "title": "リーダブルコード"
  },
  "reservedDate": "2024-01-01",
  "expiryDate": "2024-01-08",
  "status": "WAITING"
}
```

**エラーレスポンス:**
- 400 Bad Request:
  - ユーザーIDが空またはnull
  - ISBNが空またはnull
  - ISBN形式が不正
- 404 Not Found:
  - 指定されたユーザーが存在しない
  - 指定されたISBNの書籍が存在しない
- 409 Conflict:
  - ユーザーが予約停止状態
  - 同じ書籍の予約が既に存在
  - ユーザーの予約上限を超過

#### PATCH /api/reservations/{reservationId}/cancel
予約キャンセル

**パスパラメータ:**
- `reservationId`: 予約ID

**レスポンス:** 200 OK
```json
{
  "id": 1,
  "userId": 1,
  "bookIsbn": "9784873117584",
  "book": {
    "isbn": "9784873117584",
    "title": "リーダブルコード"
  },
  "reservedDate": "2024-01-01",
  "expiryDate": "2024-01-08",
  "status": "CANCELLED"
}
```

**エラーレスポンス:**
- 404 Not Found: 指定された予約が存在しない
- 409 Conflict: キャンセル不可能な状態の予約

#### PATCH /api/reservations/{reservationId}/fulfill
予約確定（貸出へ変換）

**パスパラメータ:**
- `reservationId`: 予約ID

**リクエストボディ:**
```json
{
  "dueDate": "2024-01-15"
}
```

**レスポンス:** 200 OK
```json
{
  "lending": {
    "id": 1,
    "userId": 1,
    "bookItemId": 1,
    "book": {
      "isbn": "9784873117584",
      "title": "リーダブルコード"
    },
    "lentDate": "2024-01-01",
    "dueDate": "2024-01-15",
    "status": "ACTIVE"
  },
  "reservation": {
    "id": 1,
    "status": "FULFILLED"
  }
}
```

**エラーレスポンス:**
- 400 Bad Request: 返却予定日が空またはnull、または不正な日付
- 404 Not Found: 指定された予約が存在しない
- 409 Conflict: 
  - 確定不可能な状態の予約
  - 利用可能な蔵書が存在しない

### 3.5 共通エラーレスポンス形式

すべてのエラーレスポンスは以下の形式で返却されます：

```json
{
  "timestamp": "2024-01-01T12:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "バリデーションエラーが発生しました",
  "details": [
    {
      "field": "email",
      "message": "メールアドレスの形式が正しくありません"
    }
  ],
  "path": "/api/users"
}
```

### 3.6 HTTPステータスコード一覧

- **200 OK**: リクエスト成功
- **201 Created**: リソース作成成功
- **400 Bad Request**: リクエストのバリデーションエラー
- **404 Not Found**: リソースが見つからない
- **409 Conflict**: リソースの競合状態
- **500 Internal Server Error**: サーバー内部エラー


## 4. その他
### テストコードについて
- プレゼンテーション層
  - コントローラのテストは`@WebMvcTest`を使って軽量に書いてください。
  - リクエストパラメータやリクエストボディが契約を満たさない時に返す400系のエラーを検証してください
  - 200 OKのときはコントローラが返すレスポンスの中身の検証は不要です。
- インフラストラクチャ層
  - DBにアクセスするクラスに関するテストは`DBUnit`を使って、検証してください。
