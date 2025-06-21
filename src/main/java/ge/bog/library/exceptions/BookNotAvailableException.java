package ge.bog.library.exceptions;

public class BookNotAvailableException extends RuntimeException{
    public BookNotAvailableException(String bookTitle) {
        super("Book with title " + bookTitle + " isn't available");
    }
}
