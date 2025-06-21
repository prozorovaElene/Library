package ge.bog.library.services;

import ge.bog.library.model.Book;

public interface BookRefreshService {
    Book refreshBook(Long bookId);
    void refreshAllBooks();
}