package ge.bog.library.controllers;

import ge.bog.library.exceptions.BookNotFoundException;
import ge.bog.library.model.Book;
import ge.bog.library.dto.RegistrationBodyDto;
import ge.bog.library.model.LoanBook;
import ge.bog.library.services.BookRefreshService;
import ge.bog.library.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRefreshService bookRefreshService;

    @PostMapping("/register")
    public Book register(@RequestBody RegistrationBodyDto body) {
        return bookService.registerByName(body.getTitle(),body.getQuantity());
    }

    @PutMapping("/deactivate/{bookId}")
    public String deactivateBook(@PathVariable Long bookId) {
        Book deactivatedBook = bookService.deactivateBook(bookId);
        if (deactivatedBook != null) {
            return String.format("Book %s has been deactivated", deactivatedBook.getTitle());
        } else {
            throw new BookNotFoundException(bookId);
        }
    }

    @PutMapping("/reactivate/{bookId}")
    public String reactivateBook(@PathVariable Long bookId) {
        Book deactivatedBook = bookService.reactivateBook(bookId);
        if (deactivatedBook != null) {
            return String.format("Book %s has been reactivated", deactivatedBook.getTitle());
        } else {
            throw new BookNotFoundException(bookId);
        }
    }

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookService.getBooks();
    }

    @GetMapping("/books/{userId}")
    public List<LoanBook> getBooksByUserId(@PathVariable Long userId) {
        return bookService.getBooksById(userId);
    }

    @PutMapping("/refresh/{bookId}")
    public Book refreshBook(@PathVariable Long bookId) {
        return bookRefreshService.refreshBook(bookId);
    }

}
