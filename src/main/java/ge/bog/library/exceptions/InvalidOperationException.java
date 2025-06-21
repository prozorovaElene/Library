package ge.bog.library.exceptions;

public class InvalidOperationException extends RuntimeException{
    public InvalidOperationException(String title) {
        super("Invalid operation on "+ title);
    }

}
