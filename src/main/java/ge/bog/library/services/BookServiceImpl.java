package ge.bog.library.services;

import ge.bog.library.enums.BookStatus;
import ge.bog.library.exceptions.*;
import ge.bog.library.model.Book;
import ge.bog.library.model.LoanBook;
import ge.bog.library.repositories.BookRepository;
import ge.bog.library.repositories.LoanBookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    public BookRepository bookRepository;

    @Autowired
    public LoanBookRepository loanBookRepository;

    /**
     * Registers a new book by its name and quantity.
     * @param name The name of the book.
     * @param quantity The quantity of the book.
     * @return The registered book.
     * @throws BookAlreadyExistsException if a book with the same title already exists.
     */
    @Transactional
    public Book registerByName(String name, Integer quantity) {
        // Check if the book with the given title already exists
        Book book = bookRepository.findByTitle(name).orElse(null);
        if (book != null) {
            log.info("Book with title {} already exists", name);
            throw new BookAlreadyExistsException(name);
        } else {
            // Create and save the new book
            Book newBook = new Book();
            newBook.setTitle(name);
            newBook.setQuantity(quantity);
            bookRepository.save(newBook);
            return newBook;
        }
    }

    /**
     * Deactivates a book by its ID.
     * @param bookId The ID of the book to deactivate.
     * @return The deactivated book.
     * @throws BookNotFoundException if the book is not found.
     */
    @Transactional
    @Override
    public Book deactivateBook(Long bookId) {
        // Retrieve the book from the repository
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            log.error("Book with id: {} not found", bookId);
            throw new BookNotFoundException(bookId);
        } else {
            // Deactivate the book
            book.setIsActive(Boolean.FALSE);
            bookRepository.save(book);
        }
        return book;
    }

    /**
     * Reactivates a book by its ID.
     * @param bookId The ID of the book to reactivate.
     * @return The reactivated book.
     * @throws BookNotFoundException if the book is not found.
     */
    @Transactional
    @Override
    public Book reactivateBook(Long bookId) {
        // Retrieve the book from the repository
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            log.error("Book with id: {} not found", bookId);
            throw new BookNotFoundException(bookId);
        } else {
            // Reactivate the book
            book.setIsActive(Boolean.TRUE);
            bookRepository.save(book);
        }
        return book;
    }

    /**
     * Retrieves all books.
     * @return A list of all books.
     */
    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    /**
     * Retrieves books reserved by a user with a given ID.
     * @param userId The ID of the user.
     * @return A list of books reserved by the user.
     */
    @Override
    public List<LoanBook> getBooksById(Long userId) {
        return loanBookRepository.findReservedBooksByUserId(userId, BookStatus.RESERVED);
    }
}