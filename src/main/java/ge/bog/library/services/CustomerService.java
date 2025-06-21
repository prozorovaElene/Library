package ge.bog.library.services;

import ge.bog.library.dto.CustomerResponseBookDto;
import ge.bog.library.dto.RatingRequestDto;
import ge.bog.library.dto.ReservationBodyDto;
import ge.bog.library.model.Customer;
import ge.bog.library.model.LoanBook;

import java.util.List;

public interface CustomerService {
    Customer registration(Customer body);

    List<CustomerResponseBookDto> getBookList();

    LoanBook reservation(ReservationBodyDto reservation);

    LoanBook bookReturn(ReservationBodyDto body);

    LoanBook rateBook(RatingRequestDto ratingBody);
}