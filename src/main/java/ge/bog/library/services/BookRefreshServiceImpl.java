package ge.bog.library.services;

import ge.bog.library.externalApi.ExternalAPI;
import ge.bog.library.dto.ApiResponseDto;
import ge.bog.library.exceptions.BookAlreadyRefreshedException;
import ge.bog.library.exceptions.BookNotFoundException;
import ge.bog.library.exceptions.ExternalApiException;
import ge.bog.library.exceptions.FailedToRefreshException;
import ge.bog.library.model.Book;
import ge.bog.library.repositories.BookRepository;
import ge.bog.library.repositories.LoanBookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class BookRefreshServiceImpl implements BookRefreshService {

    @Autowired
    public BookRepository bookRepository;

    @Autowired
    public LoanBookRepository loanBookRepository;

    private final ExternalAPI externalApiService;

    // Constructor-based dependency injection for ExternalAPI
    public BookRefreshServiceImpl(ExternalAPI externalApiService) {
        this.externalApiService = externalApiService;
    }

    /**
     * Refreshes the book information for a given book ID.
     * @param bookId The ID of the book to be refreshed.
     * @return The refreshed book.
     * @throws BookNotFoundException if the book is not found.
     * @throws BookAlreadyRefreshedException if the book has already been refreshed.
     * @throws ExternalApiException if there is an error while calling the external API.
     */
    @Transactional
    public Book refreshBook(Long bookId) {
        // Retrieve the book from the repository
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            log.error("Book with id {} not found", bookId);
            throw new BookNotFoundException(bookId);
        } else if (book.getIsRefreshed()) {
            log.error("book with id {} is already refreshed", bookId);
            throw new BookAlreadyRefreshedException(bookId);
        }

        try {
            // Call the external API to get updated book information
            ApiResponseDto importedObj = externalApiService.callExternalApi(book.getTitle());

            // Update the book with the new information
            book.setNumFound(importedObj.getNumFound());
            book.setNumFoundExact(importedObj.getNumFoundExact());
            book.setIsRefreshed(true);
            bookRepository.save(book);
        } catch (Exception e) {
            log.error("Error while calling external API: {}", e.getMessage());
            throw new ExternalApiException(e.getMessage());
        }
        return book;
    }

    /**
     * Refreshes non-refreshed books in the repository.
     * Throws a FailedToRefreshException if any book fails to refresh.
     */
    public void refreshAllBooks() {
        // Retrieve all books from the repository
        List<Book> books = bookRepository.findAll();
        for (Book book : books) {
            // Refresh only active and non-refreshed books
            if (!book.getIsRefreshed()) {
                try {
                    refreshBook(book.getBookId());
                } catch (Exception e) {
                    log.error("Failed to refresh book with ID {}: {}", book.getBookId(), e.getMessage());
                    throw new FailedToRefreshException(book.getBookId(),e.getMessage());
                }
            }
        }
    }
}