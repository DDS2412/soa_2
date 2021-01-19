package soa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import soa.model.SpaceMarine;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name="PageableSpaceMarinesDto")
@XmlAccessorType(XmlAccessType.FIELD)
public class PageableSpaceMarinesDto {
    private List<SpaceMarine> spaceMarines;

    private Integer pageNumber;

    private Integer totalPages;

    private Integer elementsAtPage;

    private Integer totalElements;

    private Boolean isLastPage;
}
