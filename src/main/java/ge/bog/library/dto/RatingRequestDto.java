package ge.bog.library.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingRequestDto {
    private Long customerId;
    private String bookTitle;
    private Double rating;
}
