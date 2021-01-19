package soa.services;

import org.springframework.stereotype.Service;
import soa.comparators.*;
import soa.exception.BadSortException;
import soa.models.SpaceMarine;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SortSpaceMarineService {
    public List<SpaceMarine> sortByParams(List<SpaceMarine> spaceMarines, String[] sortParams, String sortState) throws BadSortException {
        sortState = sortState == null ? "asc" : sortState;
        for (String param : sortParams){
            if (param.equals("")){
                continue;
            }

            spaceMarines = spaceMarines
                    .stream()
                    .sorted(getComparator(param, sortState))
                    .collect(Collectors.toList());
        }

        return spaceMarines;
    }

    private Comparator<SpaceMarine> getComparator(String param, String sortState) throws BadSortException {
        Boolean sortStateBoolean = sortState.equals("asc");

        switch (param){
            case "name":{
                return new SpaceMarineNameComparator(sortStateBoolean);
            }
            case "health": {
                return new SpaceMarineHealthComparator(sortStateBoolean);
            }
            case "height": {
                return new SpaceMarineHeightComparator(sortStateBoolean);
            }
            case "category": {
                return new SpaceMarineCategoryComparator(sortStateBoolean);
            }
            case "meleeWeapon": {
                return new SpaceMarineMeleeWeaponComparator(sortStateBoolean);
            }
            case "chapterName": {
                return new SpaceMarineChapterNameComparator(sortStateBoolean);
            }
            case "chapterMarinesCount": {
                return new SpaceMarineChapterMarinesCountComparator(sortStateBoolean);
            }
            case "xPosition": {
                return new SpaceMarineXPositionComparator(sortStateBoolean);
            }
            case "yPosition": {
                return new SpaceMarineYPositionComparator(sortStateBoolean);
            }
            default:{
                throw new BadSortException();
            }
        }
    }
}
