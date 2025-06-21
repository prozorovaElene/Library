package ge.bog.library.exceptions;

public class FailedToRefreshException extends RuntimeException{
    public FailedToRefreshException(Long bookId, String message) {
        super("Failed to refresh book with ID: "+bookId +" : "+message);
    }
}
