package soa.comparators;

import soa.models.SpaceMarine;

import java.util.Comparator;

public class SpaceMarineHealthComparator implements Comparator<SpaceMarine> {

    private Boolean sortState;

    public SpaceMarineHealthComparator(Boolean sortState){
        this.sortState = sortState;
    }

    @Override
    public int compare(SpaceMarine spaceMarine1, SpaceMarine spaceMarine2) {
        int result = Long.compare(spaceMarine1.getHealth(), spaceMarine2.getHealth());
        return sortState ? result : -result;
    }
}
