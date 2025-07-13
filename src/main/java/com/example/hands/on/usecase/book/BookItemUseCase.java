package com.example.hands.on.usecase.book;

import com.example.hands.on.usecase.book.command.CreateBookItemCommand;
import com.example.hands.on.usecase.book.command.UpdateBookItemCommand;
import com.example.hands.on.usecase.book.dto.BookItemDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookItemUseCase {

    public List<BookItemDto> getAllBookItems() {
        // TODO: Implement get all book items logic
        // 1. Query all book items from repository
        // 2. Convert domain entities to DTOs
        // 3. Return list of book item DTOs
        return null;
    }

    public BookItemDto getBookItemById(Long id) {
        // TODO: Implement get book item by ID logic
        // 1. Validate ID
        // 2. Query repository by ID
        // 3. Throw exception if book item not found
        // 4. Convert domain entity to DTO
        // 5. Return book item DTO
        return null;
    }

    public List<BookItemDto> getBookItemsByIsbn(String isbn) {
        // TODO: Implement get book items by ISBN logic
        // 1. Validate ISBN
        // 2. Query book items by ISBN
        // 3. Convert domain entities to DTOs
        // 4. Return list of book item DTOs
        return null;
    }

    public List<BookItemDto> getAvailableBookItemsByIsbn(String isbn) {
        // TODO: Implement get available book items by ISBN logic
        // 1. Validate ISBN
        // 2. Query available book items by ISBN (status = AVAILABLE)
        // 3. Convert domain entities to DTOs
        // 4. Return list of available book item DTOs
        return null;
    }

    public BookItemDto createBookItem(CreateBookItemCommand command) {
        // TODO: Implement book item creation logic
        // 1. Validate command parameters
        // 2. Check if book with given ISBN exists
        // 3. Create book item domain entity
        // 4. Set initial status to AVAILABLE
        // 5. Save to repository
        // 6. Convert saved entity to DTO
        // 7. Return book item DTO
        return null;
    }

    public BookItemDto updateBookItem(UpdateBookItemCommand command) {
        // TODO: Implement book item update logic
        // 1. Validate command parameters
        // 2. Get existing book item by ID
        // 3. Throw exception if book item not found
        // 4. Update allowed fields (location, status)
        // 5. Validate status transitions
        // 6. Save updated entity to repository
        // 7. Convert updated entity to DTO
        // 8. Return updated book item DTO
        return null;
    }

    public void deleteBookItem(Long id) {
        // TODO: Implement book item deletion logic
        // 1. Validate ID
        // 2. Check if book item exists
        // 3. Check if book item can be deleted (not currently lent or reserved)
        // 4. Delete from repository
        // 5. Handle related data cleanup if necessary
        return;
    }
}