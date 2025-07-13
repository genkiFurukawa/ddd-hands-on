CREATE TABLE authors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE publishers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE books (
    isbn VARCHAR(13) PRIMARY KEY,
    title VARCHAR(500) NOT NULL,
    publisher_id BIGINT NOT NULL,
    FOREIGN KEY (publisher_id) REFERENCES publishers(id)
);

CREATE TABLE book_authors (
    book_isbn VARCHAR(13) NOT NULL,
    author_id BIGINT NOT NULL,
    PRIMARY KEY (book_isbn, author_id),
    FOREIGN KEY (book_isbn) REFERENCES books(isbn),
    FOREIGN KEY (author_id) REFERENCES authors(id)
);

CREATE TABLE book_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    isbn VARCHAR(13) NOT NULL,
    status INT NOT NULL DEFAULT 1,
    FOREIGN KEY (isbn) REFERENCES books(isbn)
);

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    registered_date DATE NOT NULL,
    status INT NOT NULL DEFAULT 1
);

CREATE TABLE lendings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    book_item_id BIGINT NOT NULL,
    lent_date DATE NOT NULL,
    due_date DATE NOT NULL,
    returned_date DATE,
    status INT NOT NULL DEFAULT 1,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (book_item_id) REFERENCES book_items(id)
);

CREATE TABLE reservations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    book_isbn VARCHAR(13) NOT NULL,
    reserved_date DATE NOT NULL,
    expiry_date DATE NOT NULL,
    status INT NOT NULL DEFAULT 1,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (book_isbn) REFERENCES books(isbn)
);

CREATE INDEX idx_book_items_isbn ON book_items(isbn);
CREATE INDEX idx_book_items_status ON book_items(status);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_status ON users(status);
CREATE INDEX idx_lendings_user_id ON lendings(user_id);
CREATE INDEX idx_lendings_book_item_id ON lendings(book_item_id);
CREATE INDEX idx_lendings_status ON lendings(status);
CREATE INDEX idx_lendings_due_date ON lendings(due_date);
CREATE INDEX idx_reservations_user_id ON reservations(user_id);
CREATE INDEX idx_reservations_book_isbn ON reservations(book_isbn);
CREATE INDEX idx_reservations_status ON reservations(status);
CREATE INDEX idx_reservations_expiry_date ON reservations(expiry_date);