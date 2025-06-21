package ge.bog.library.model;

import ge.bog.library.enums.BookStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "LOANBOOK")
@SequenceGenerator(name="loanBook_seq", allocationSize=1)
public class LoanBook {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="loanBook_seq")
    private Long id;

    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(name ="BOOK_ID")
    private String bookTitle;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private BookStatus status;

    @Column(name = "RATING")
    @Min(0)
    @Max(5)
    private Double rating;
}
