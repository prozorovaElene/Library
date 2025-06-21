package ge.bog.library.exceptions;

public class ExternalApiException extends RuntimeException{
    public ExternalApiException(String message) {
        super("Error while calling external API: " + message);
    }
}
