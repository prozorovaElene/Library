package ge.bog.library.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "BOOK")
@SequenceGenerator(name="book_seq", allocationSize=1)
public class Book {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="book_seq")
    private Long bookId;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "NUM_FOUND")
    private Integer numFound;

    @Column(name = "NUM_FOUND_EXACT")
    private Boolean numFoundExact;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive = true;

    @Column(name = "IS_REFRESHED")
    private Boolean isRefreshed = false;

    @Column(name = "RESERVATION_COUNT")
    private Integer reservationCnt = 0;


    public Book(String title, Integer numFound, Integer quantity, Boolean numFoundExact) {
        this.title = title;
        this.numFoundExact = numFoundExact;
        this.quantity = quantity;
        this.numFound = numFound;
    }
}
