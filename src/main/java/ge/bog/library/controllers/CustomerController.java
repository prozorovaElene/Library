package ge.bog.library.controllers;

import ge.bog.library.dto.RatingRequestDto;
import ge.bog.library.dto.ReservationBodyDto;
import ge.bog.library.model.Customer;
import ge.bog.library.model.LoanBook;
import ge.bog.library.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ge.bog.library.dto.CustomerResponseBookDto;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public Customer register(@RequestBody Customer body) {
        return customerService.registration(body);
    }

    @GetMapping("/books")
    public List<CustomerResponseBookDto> getBooks() {
        return customerService.getBookList();
    }

    @PostMapping("/reserve")
    public LoanBook reserve(@RequestBody ReservationBodyDto reservation) {
        return customerService.reservation(reservation);
    }

    @PutMapping("/return")
    public LoanBook returnBook(@RequestBody ReservationBodyDto body) {
        return customerService.bookReturn(body);
    }

    @PutMapping("/rate")
    public LoanBook BookRating(@RequestBody RatingRequestDto ratingBody) {
        return customerService.rateBook(ratingBody);
    }
}
