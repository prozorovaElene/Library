package ge.bog.library.exceptions;

public class CsvFileException extends RuntimeException {
    public CsvFileException(String message) {
        super("Error writing reservation CSV file: " + message);
    }
}
