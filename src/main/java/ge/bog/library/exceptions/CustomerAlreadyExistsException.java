package ge.bog.library.exceptions;

public class CustomerAlreadyExistsException extends RuntimeException{
    public CustomerAlreadyExistsException(){
        super("Customer already exists");
    }
}
