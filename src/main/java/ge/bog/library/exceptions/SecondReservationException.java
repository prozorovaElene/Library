package ge.bog.library.exceptions;

public class SecondReservationException extends RuntimeException{
    public SecondReservationException(String bookTitle) {
        super("You have already once reserved a book with title " + bookTitle);
    }
}
