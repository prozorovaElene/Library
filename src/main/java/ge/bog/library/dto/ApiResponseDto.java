package ge.bog.library.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApiResponseDto {
    private Integer numFound;
    private Integer start;
    private Boolean numFoundExact;
    private List<Object> docs;
    private Integer num_found;
    private String q;
    private Integer offset;
}