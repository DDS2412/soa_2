package soa.services;

import soa.dto.FilteringObjectDto;
import soa.dto.PageableSpaceMarinesDto;
import soa.exception.BadSortException;
import soa.exception.BadSpaceMarineCategory;
import soa.exception.BadSpaceMarineIdException;
import soa.models.SpaceMarine;

import java.util.List;

public interface SpaceMarineService {
    void saveSpaceMarine(SpaceMarine spaceMarine);

    void updateSpaceMarine(Integer spaceMarineId, SpaceMarine updatedSpaceMarine) throws BadSpaceMarineIdException;

    void deleteSpaceMarineById(Integer spaceMarineId) throws BadSpaceMarineIdException;

    void deleteSpaceMarineByHealth(long health);

    PageableSpaceMarinesDto findAllSpaceMarines(
            Integer atPage,
            Integer pageNumber,
            FilteringObjectDto filteringObjectDto,
            String[] sortParams,
            String sortState) throws BadSortException;

    SpaceMarine findSpaceMarine(Integer id) throws BadSpaceMarineIdException;

    float calculateHealthMeanValue(List<Integer> spaceMarineIds) throws BadSpaceMarineIdException;

    List<SpaceMarine> findSpaceMarineWhenCategoryGreater(Integer intCategory) throws BadSpaceMarineCategory;
}
