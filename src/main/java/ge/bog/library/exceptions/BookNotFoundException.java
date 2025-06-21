package ge.bog.library.exceptions;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Long bookId) {
        super("Book with id " + bookId + " not found");
    }
    public BookNotFoundException(String bookName) {
        super("Book with title " +bookName+ " doesn't exist");
    }


}
