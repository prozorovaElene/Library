package ge.bog.library.exceptions;

public class BookNotInStockException extends RuntimeException{
    public BookNotInStockException(String bookTitle) {
        super("Book with title " + bookTitle + " is not in stock");
    }
}
