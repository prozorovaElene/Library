package ge.bog.library.services;

import ge.bog.library.model.Book;
import ge.bog.library.model.LoanBook;

import java.util.List;

public interface BookService {
    Book registerByName(String name, Integer quantity);

    Book deactivateBook(Long bookId);

    Book reactivateBook(Long bookId);

    List<Book> getBooks();

    List<LoanBook> getBooksById(Long userId);
}