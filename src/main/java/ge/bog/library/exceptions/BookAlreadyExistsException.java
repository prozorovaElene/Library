package ge.bog.library.exceptions;

public class BookAlreadyExistsException extends RuntimeException{
    public BookAlreadyExistsException(String name) {
        super("Book with title " + name + " already exists");
    }

}
