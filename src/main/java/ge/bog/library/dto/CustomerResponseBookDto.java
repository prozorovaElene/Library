package ge.bog.library.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseBookDto {
    private Long bookId;
    private String title;
    private Integer quantity;
}
