package ge.bog.library.repositories;

import ge.bog.library.enums.BookStatus;
import ge.bog.library.model.LoanBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoanBookRepository extends JpaRepository<LoanBook, Long> {

    LoanBook findByBookTitleAndCustomerId(String bookTitle, Long customerId);

    @Query("SELECT lb FROM LoanBook lb WHERE lb.bookTitle = :bookTitle AND lb.customerId = :customerId AND lb.status = :status")
    LoanBook findReadBookByTitleAndCustomerId(@Param("bookTitle") String bookTitle,
                                              @Param("customerId") Long customerId,
                                              @Param("status") BookStatus status);

    @Query("SELECT lb FROM LoanBook lb WHERE lb.status = :status AND lb.customerId = :userId")
    List<LoanBook> findReservedBooksByUserId(@Param("userId") Long userId,
                                             @Param("status") BookStatus status);

    @Query("SELECT lb FROM LoanBook lb WHERE lb.status = :status")
    List<LoanBook> findReservedBooks(@Param("status") BookStatus status);
}
