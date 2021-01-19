package soa.comparators;

import soa.models.SpaceMarine;

import java.util.Comparator;

public class SpaceMarineCategoryComparator implements Comparator<SpaceMarine> {

    private Boolean sortState;

    public SpaceMarineCategoryComparator(Boolean sortState){
        this.sortState = sortState;
    }

    @Override
    public int compare(SpaceMarine spaceMarine1, SpaceMarine spaceMarine2) {
        int result = spaceMarine1.getCategory().compareTo(spaceMarine2.getCategory());
        return sortState ? result : -result;
    }
}
