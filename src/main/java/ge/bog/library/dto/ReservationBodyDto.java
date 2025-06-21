package ge.bog.library.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationBodyDto {
    private Long customerId;
    private String bookTitle;
}
