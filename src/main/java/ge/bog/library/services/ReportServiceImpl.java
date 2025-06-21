package ge.bog.library.services;

import com.opencsv.CSVWriter;
import ge.bog.library.enums.BookStatus;
import ge.bog.library.exceptions.CsvFileException;
import ge.bog.library.model.Book;
import ge.bog.library.model.LoanBook;
import ge.bog.library.repositories.BookRepository;
import ge.bog.library.repositories.LoanBookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private LoanBookRepository loanBookRepository;

    @Autowired
    private BookRepository bookRepository;

    /**
     * Generates a CSV report of reserved books.
     * @return A message indicating the success of the report generation.
     * @throws CsvFileException if there is an error writing the CSV file.
     */
    @Override
    public String reservationReport() {
        // Retrieve a list of all reserved books from the repository
        List<LoanBook> reservedBooks = loanBookRepository.findReservedBooks(BookStatus.RESERVED);

        // Define the CSV file name and its absolute path
        String fileName = "reserved_books_report.csv";
        String filePath = Paths.get(fileName).toAbsolutePath().toString();

        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            // Write the header row to the CSV file
            String[] header = {"Customer ID", "Book Title", "Status", "Rating"};
            writer.writeNext(header);

            // Write the data rows to the CSV file
            reservedBooks.forEach(loanBook -> {
                String[] data = {
                        loanBook.getCustomerId().toString(),
                        loanBook.getBookTitle(),
                        loanBook.getStatus() != null ? loanBook.getStatus().name() : "N/A",
                        loanBook.getRating() != null ? loanBook.getRating().toString() : "N/A"
                };
                writer.writeNext(data);
            });
            writer.flush();
        } catch (IOException e) {
            log.error("Error writing reservation CSV file: {}", e.getMessage());
            throw new CsvFileException(e.getMessage());
        }
        return "Reservation report generated successfully.";
    }

    /**
     * Generates a CSV report for the admin, including all books and their reservation counts.
     * @return A message indicating the success of the report generation.
     * @throws CsvFileException if there is an error writing the CSV file.
     */
    @Override
    public String adminReport() {
        // Retrieve a list of all books from the repository
        List<Book> allBooks = bookRepository.findAll();

        // Define the CSV file name and its absolute path
        String fileName = "admin_books_report.csv";
        String filePath = Paths.get(fileName).toAbsolutePath().toString();

        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            // Write the header row to the CSV file
            String[] header = {"Book Title", "Reservation Count"};
            writer.writeNext(header);

            // Write the data rows to the CSV file
            allBooks.forEach(book -> {
                String[] data = {
                        book.getTitle(),
                        String.valueOf(book.getReservationCnt())
                };
                writer.writeNext(data);
            });
            writer.flush();
        } catch (IOException e) {
            log.error("Error writing admin CSV file: {}", e.getMessage());
            throw new CsvFileException(e.getMessage());
        }
        return "Admin report generated successfully.";
    }
}