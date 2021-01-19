package soa.comparators;

import soa.models.SpaceMarine;

import java.util.Comparator;

public class SpaceMarineNameComparator implements Comparator<SpaceMarine> {
    private Boolean sortState;

    public SpaceMarineNameComparator(Boolean sortState){
        this.sortState = sortState;
    }

    @Override
    public int compare(SpaceMarine spaceMarine1, SpaceMarine spaceMarine2) {
        int result = spaceMarine1.getName().compareTo(spaceMarine2.getName());
        return sortState ? result : -result;
    }
}
