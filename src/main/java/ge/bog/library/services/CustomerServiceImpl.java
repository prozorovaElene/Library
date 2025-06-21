package ge.bog.library.services;

import ge.bog.library.dto.CustomerResponseBookDto;
import ge.bog.library.dto.RatingRequestDto;
import ge.bog.library.dto.ReservationBodyDto;
import ge.bog.library.enums.BookStatus;
import ge.bog.library.exceptions.*;
import ge.bog.library.model.Book;
import ge.bog.library.model.Customer;
import ge.bog.library.model.LoanBook;
import ge.bog.library.repositories.BookRepository;
import ge.bog.library.repositories.CustomerRepository;
import ge.bog.library.repositories.LoanBookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LoanBookRepository loanBookRepository;

    /**
     * Registers a new customer.
     * @param body The customer details.
     * @return The registered customer.
     * @throws CustomerAlreadyExistsException if a customer with the same email already exists.
     */
    @Transactional
    @Override
    public Customer registration(Customer body) {
        Customer customer = customerRepository.findByEmail(body.getEmail());
        if (customer == null) {
            return customerRepository.save(body);
        } else {
            log.error("Customer already exists");
            throw new CustomerAlreadyExistsException();
        }
    }

    /**
     * Retrieves a list of all refreshed books.
     * @return A list of CustomerResponseBook containing book details.
     */
    @Override
    public List<CustomerResponseBookDto> getBookList() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .filter(Book::getIsRefreshed)
                .map(book -> new CustomerResponseBookDto(book.getBookId(), book.getTitle(), book.getQuantity()))
                .collect(Collectors.toList());
    }

    /**
     * Reserves a book for a customer.
     * @param reservation The reservation details.
     * @return The loan book details.
     * @throws CustomerNotFoundException if the customer is not found.
     * @throws BookNotInStockException if the book is not in stock.
     * @throws BookNotAvailableException if the book is not available.
     * @throws SecondReservationException if the book is already reserved by the customer.
     */
    @Transactional
    @Override
    public LoanBook reservation(ReservationBodyDto reservation) {
        Book book = bookRepository.findByTitle(reservation.getBookTitle()).orElse(null);
        LoanBook loanBook = loanBookRepository.findByBookTitleAndCustomerId(reservation.getBookTitle(), reservation.getCustomerId());
        Customer customer = customerRepository.findById(reservation.getCustomerId()).orElse(null);

        if (customer == null) {
            log.info("Customer with id {} not found", reservation.getCustomerId());
            throw new CustomerNotFoundException(reservation.getCustomerId());
        } else if (loanBook == null && book != null && book.getIsActive() && book.getIsRefreshed() && book.getQuantity() > 0) {
            // Reserve the book
            book.setQuantity(book.getQuantity() - 1);
            book.setReservationCnt(book.getReservationCnt() + 1);
            LoanBook newLoanBook = new LoanBook();
            newLoanBook.setCustomerId(reservation.getCustomerId());
            newLoanBook.setBookTitle(reservation.getBookTitle());
            newLoanBook.setStatus(BookStatus.RESERVED);
            bookRepository.save(book);
            loanBookRepository.save(newLoanBook);
            return newLoanBook;
        } else if (loanBook == null && book != null && book.getIsActive() && book.getIsRefreshed()) {
            log.info("Book with title {} is not in stock", reservation.getBookTitle());
            throw new BookNotInStockException(reservation.getBookTitle());
        } else if (loanBook == null) {
            log.info("Book with title {} isn't available", reservation.getBookTitle());
            throw new BookNotAvailableException(reservation.getBookTitle());
        } else {
            log.info("Book with title: {} can't be reserved twice per person", reservation.getBookTitle());
            throw new SecondReservationException(reservation.getBookTitle());
        }
    }

    /**
     * Returns a reserved book.
     * @param body The reservation details.
     * @return The updated loan book details.
     * @throws InvalidOperationException if the operation is invalid.
     * @throws BookNotFoundException if the book is not found.
     */
    @Transactional
    @Override
    public LoanBook bookReturn(ReservationBodyDto body) {
        Book book = bookRepository.findByTitle(body.getBookTitle()).orElse(null);
        LoanBook loanBook = loanBookRepository.findByBookTitleAndCustomerId(body.getBookTitle(), body.getCustomerId());
        if (book != null && !Objects.equals(loanBook.getStatus(), "read")) {
            // Return the book
            book.setQuantity(book.getQuantity() + 1);
            bookRepository.save(book);
            loanBook.setStatus(BookStatus.READ);
            loanBookRepository.save(loanBook);
            return loanBook;
        } else if (book != null) {
            log.info("Invalid operation on {}", body.getBookTitle());
            throw new InvalidOperationException(body.getBookTitle());
        } else {
            log.info("Book with title: {} doesn't exist", body.getBookTitle());
            throw new BookNotFoundException(body.getBookTitle());
        }
    }

    /**
     * Rates a book that has been read by the customer.
     * @param ratingBody The rating details.
     * @return The updated loan book details.
     * @throws InvalidRatingException if the rating is invalid.
     * @throws InvalidOperationException if the operation is invalid.
     */
    @Transactional
    @Override
    public LoanBook rateBook(RatingRequestDto ratingBody) {
        if (ratingBody.getRating() < 0 || ratingBody.getRating() > 5) {
            throw new InvalidRatingException();
        }

        LoanBook loanBook = loanBookRepository.findReadBookByTitleAndCustomerId(
                ratingBody.getBookTitle(),
                ratingBody.getCustomerId(),
                BookStatus.READ
        );

        if (loanBook == null) {
            log.error("Invalid operation on {}", ratingBody.getBookTitle());
            throw new InvalidOperationException(ratingBody.getBookTitle());
        }

        loanBook.setRating(ratingBody.getRating());
        return loanBookRepository.save(loanBook);
    }

}