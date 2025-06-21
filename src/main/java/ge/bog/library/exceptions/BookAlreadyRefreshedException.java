package ge.bog.library.exceptions;

public class BookAlreadyRefreshedException extends RuntimeException{
    public BookAlreadyRefreshedException(Long bookId) {
        super("Book with id " + bookId + " is already refreshed");
    }

}
