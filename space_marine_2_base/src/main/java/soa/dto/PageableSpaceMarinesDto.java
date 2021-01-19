package soa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import soa.models.SpaceMarine;

import java.util.List;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class PageableSpaceMarinesDto {
    private List<SpaceMarine> spaceMarines;

    private Integer pageNumber;

    private Integer totalPages;

    private Integer elementsAtPage;

    private Integer totalElements;

    private Boolean isLastPage;
}
